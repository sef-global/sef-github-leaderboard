package org.sefglobal.githubleaderboad.api.dao;

import java.sql.PreparedStatement;
import java.util.*;

import org.sefglobal.githubleaderboad.api.beans.PaginatedResult;
import org.sefglobal.githubleaderboad.api.beans.PaginatedScoreResult;
import org.sefglobal.githubleaderboad.api.beans.Score;
import org.sefglobal.githubleaderboad.api.beans.UserWithPoints;
import org.sefglobal.githubleaderboad.api.exception.ResourceNotFoundException;
import org.sefglobal.githubleaderboad.api.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ScoreDAO {

    private Logger logger = LoggerFactory.getLogger(ScoreDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Score addScore(Score score) throws ResourceNotFoundException {
        String sqlQuery = "" +
                "INSERT INTO" +
                "   score(" +
                "       user_id, " +
                "       pr_url, " +
                "       created_at"+
                "   ) " +
                "VALUES " +
                "   (?,?,?)";

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"id"});
                ps.setInt(1, score.getUserId());
                ps.setString(2, score.getPrUrl());
                ps.setLong(3, new Date().getTime() / 1000);
                return ps;
            }, keyHolder);
            int key = keyHolder.getKey().intValue();
            return getScoreById(key);
        } catch (DataAccessException e) {
            logger.error("Unable to get Entity info", e);
        }
        return null;
    }

    public Score getScoreById(int id) throws ResourceNotFoundException {
        String sqlQuery = "" +
                "SELECT " +
                "   * " +
                "FROM " +
                "   score " +
                "WHERE " +
                "   id=? ";

        try {
            return jdbcTemplate.queryForObject(
                    sqlQuery,
                    new Object[]{id},
                    (rs, rowNum) -> BeanUtil.getScoreFromResultSet(rs)
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get info of '" + id + "'", e);
            throw new ResourceNotFoundException("Score not found");
        }
    }

    public PaginatedScoreResult getPaginatedScoreByEntityId(int entityId, int limit, int offset, Long from, Long to)
            throws ResourceNotFoundException {

        String sqlQuery = "" +
                "SELECT " +
                "   COUNT(id) as count " +
                "FROM " +
                "   score " +
                "WHERE " +
                "   user_id=? ";

        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(entityId);

        if (from != null && to != null) {
            sqlQuery = sqlQuery + " AND created_at BETWEEN ? AND ? ";
            parameters.add(from);
            parameters.add(to);
        }
        final Map map = new HashMap<String, Integer>();
        try {
            jdbcTemplate.queryForObject(
                    sqlQuery,
                    parameters.toArray(),
                    (rs, rowNum) -> {
                        map.put("count", rs.getInt("count"));
                        return null;
                    }
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get entity info", e);
            throw new ResourceNotFoundException();
        }

        int count = (Integer) map.get("count");
        int points = count * 10;

        return new PaginatedScoreResult(count, points, getScoreByEntityId(entityId, limit, offset, from, to));
    }

    public List<Score> getScoreByEntityId(int id, int limit, int offset, Long from, Long to) {
        String sqlQuery = "" +
                "SELECT " +
                "   * " +
                "FROM " +
                "   score " +
                "WHERE " +
                "   user_id=? ";
        if (from != null || to != null) {
            sqlQuery = sqlQuery + " AND created_at BETWEEN ? AND ? ";
        }
        sqlQuery = sqlQuery +
                "ORDER BY " +
                "   id DESC " +
                "LIMIT " +
                "   ?, ?";

        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(id);
        if (from != null && to != null) {
            parameters.add(from);
            parameters.add(to);
        }
        parameters.add(offset);
        parameters.add(limit);

        try {
            return jdbcTemplate.query(
                    sqlQuery,
                    parameters.toArray(),
                    (rs, rowNum) -> BeanUtil.getScoreFromResultSet(rs)
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get user info", e);
        }
        return null;
    }

    public PaginatedResult getPaginatedUsersWithPoints(int limit, int offset, Long from, Long to)
            throws ResourceNotFoundException {
        ArrayList<Object> parameters = new ArrayList<>();
        String sqlQuery = "" +
                "SELECT " +
                "    COUNT(DISTINCT (u.id)) as `count` " +
                "FROM " +
                "       user u " +
                "   INNER JOIN " +
                "       score s " +
                "   ON " +
                "       u.id = s.user_id ";
        if (from != null && to != null) {
            sqlQuery = sqlQuery + "WHERE created_at BETWEEN ? AND ? ";
            parameters.add(from);
            parameters.add(to);
        }

        int count;
        try {
            count = jdbcTemplate.queryForObject(
                    sqlQuery,
                    parameters.toArray(),
                    (rs, rowNum) -> rs.getInt("count")
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get entity info", e);
            throw new ResourceNotFoundException();
        }

        return new PaginatedResult(count, getEntityWithPoints(limit, offset, from, to));
    }

    public List<UserWithPoints> getEntityWithPoints(int limit, int offset, Long from, Long to) {
       String sqlQuery = "" +
               "SELECT " +
               "   e.*, " +
               "   COUNT(s.id) as points, " +
               "   RANK() " +
               "       OVER (" +
               "           ORDER BY COUNT(s.id) DESC " +
               "       ) `rank` " +
               "FROM " +
               "       user e " +
               "   INNER JOIN " +
               "       score s " +
               "   ON " +
               "       e.id = s.user_id ";

        if (from != null && to != null) {
            sqlQuery = sqlQuery + "WHERE created_at BETWEEN ? AND ? ";
        }
        sqlQuery = sqlQuery +
                "GROUP BY " +
                "   user_id " +
                "ORDER BY " +
                "   points DESC " +
                "LIMIT ?, ? ";

        ArrayList<Object> parameters = new ArrayList<>();

        if (from != null && to != null) {
            parameters.add(from);
            parameters.add(to);
        }
        parameters.add(offset);
        parameters.add(limit);


        try {
            return jdbcTemplate.query(
                    sqlQuery,
                    parameters.toArray(),
                    (rs, rowNum) -> BeanUtil.getEntityWithPointsFromResultSet(rs)
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get entity info", e);
        }
        return null;
    }
}
