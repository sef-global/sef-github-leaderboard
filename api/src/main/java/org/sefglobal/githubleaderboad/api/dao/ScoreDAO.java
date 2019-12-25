package org.sefglobal.githubleaderboad.api.dao;

import org.sefglobal.githubleaderboad.api.beans.UserWithPoints;
import org.sefglobal.githubleaderboad.api.beans.PaginatedResult;
import org.sefglobal.githubleaderboad.api.beans.PaginatedScoreResult;
import org.sefglobal.githubleaderboad.api.beans.Score;
import org.sefglobal.githubleaderboad.api.exception.BadRequestException;
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

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScoreDAO {

    private Logger logger = LoggerFactory.getLogger(ScoreDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Score addScore(Score score) throws BadRequestException, ResourceNotFoundException {
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

    public PaginatedScoreResult getPaginatedScoreByEntityId(int entityId, int limit, int offset) throws ResourceNotFoundException {

        String sqlQuery = "" +
                "SELECT " +
                "   COUNT(id) as count " +
                "FROM " +
                "   score " +
                "WHERE " +
                "   user_id=? ";
        final Map map = new HashMap<String, Integer>();
        try {
            jdbcTemplate.queryForObject(
                    sqlQuery,
                    new Object[]{entityId},
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

        return new PaginatedScoreResult(count, points, getScoreByEntityId(entityId, limit, offset));
    }

    public List<Score> getScoreByEntityId(int id, int limit, int offset) {
        String sqlQuery = "" +
                "SELECT " +
                "   * " +
                "FROM " +
                "   score " +
                "WHERE " +
                "   user_id=? " +
                "ORDER BY " +
                "   id DESC " +
                "LIMIT " +
                "   ?, ?";

        try {
            return jdbcTemplate.query(
                    sqlQuery,
                    new Object[]{id, offset, limit},
                    (rs, rowNum) -> BeanUtil.getScoreFromResultSet(rs)
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get user info", e);
        }
        return null;
    }

    public PaginatedResult getPaginatedUsersWithPoints(int limit, int offset) throws ResourceNotFoundException {
        String sqlQuery = "" +
                "SELECT " +
                "    COUNT(DISTINCT (u.id)) as `count` " +
                "FROM " +
                "       user u " +
                "   INNER JOIN " +
                "       score s " +
                "   ON " +
                "       u.id = s.user_id ";

        int count;
        try {
            count = jdbcTemplate.queryForObject(
                    sqlQuery,
                    (rs, rowNum) -> rs.getInt("count")
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get entity info", e);
            throw new ResourceNotFoundException();
        }

        return new PaginatedResult(count, getEntityWithPoints(limit, offset));
    }

    public List<UserWithPoints> getEntityWithPoints(int limit, int offset) {
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
                "       e.id = s.user_id " +
                "GROUP BY " +
                "   user_id " +
                "ORDER BY " +
                "   points DESC " +
                "LIMIT " +
                "   ?, ?";

        try {
            return jdbcTemplate.query(
                    sqlQuery,
                    new Object[]{offset, limit},
                    (rs, rowNum) -> BeanUtil.getEntityWithPointsFromResultSet(rs)
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get entity info", e);
        }
        return null;
    }
}
