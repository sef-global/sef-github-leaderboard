package org.sefglobal.githubleaderboad.api.dao;

import org.sefglobal.giraffe.api.beans.*;
import org.sefglobal.githubleaderboad.api.beans.EntityWithPoints;
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

import javax.servlet.http.HttpServletResponse;
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
                "       entity_id, " +
                "       description, " +
                "       points, " +
                "       created_at, " +
                "       status" +
                "   ) " +
                "VALUES " +
                "   (?,?,?,?,'ACTIVE')";

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"id"});
                ps.setInt(1, score.getEntityId());
                ps.setString(2, score.getDescription());
                ps.setInt(3, score.getPoints());
                ps.setLong(4, new Date().getTime() / 1000);
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
                "   id=? " +
                "   AND " +
                "   status='ACTIVE'";

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

    public void removeScore(int id, HttpServletResponse response) throws ResourceNotFoundException {
        String sqlQuery = "" +
                "UPDATE " +
                "   score " +
                "SET " +
                "   status = 'REMOVED' " +
                "WHERE " +
                "   id = ?";

        try {
            jdbcTemplate.update(sqlQuery, id);
        } catch (DataAccessException e) {
            logger.error("Unable remove '" + id + "'", e);
            throw new ResourceNotFoundException("Score not found");
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    public PaginatedScoreResult getPaginatedScoreByEntityId(int entityId, int limit, int offset) throws ResourceNotFoundException {

        String sqlQuery = "" +
                "SELECT " +
                "   COUNT(id) as count, " +
                "   SUM(points) as points " +
                "FROM " +
                "   score " +
                "WHERE " +
                "   entity_id=? " +
                "   AND " +
                "   status='ACTIVE'";
        final Map map = new HashMap<String, Integer>();
        try {
            jdbcTemplate.queryForObject(
                    sqlQuery,
                    new Object[]{entityId},
                    (rs, rowNum) -> {
                        map.put("count", rs.getInt("count"));
                        map.put("points", rs.getInt("points"));
                        return null;
                    }
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get entity info", e);
            throw new ResourceNotFoundException();
        }

        int count = (Integer) map.get("count");
        int points = (Integer) map.get("points");

        return new PaginatedScoreResult(count, points, getScoreByEntityId(entityId, limit, offset));
    }

    public List<Score> getScoreByEntityId(int id, int limit, int offset) {
        String sqlQuery = "" +
                "SELECT " +
                "   * " +
                "FROM " +
                "   score " +
                "WHERE " +
                "   entity_id=? " +
                "   AND " +
                "   status='ACTIVE' " +
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
            logger.error("Unable to get entity info", e);
        }
        return null;
    }

    public PaginatedResult getPaginatedEntitiesWithPointsByBoardId(int boardId, int limit, int offset) throws ResourceNotFoundException {
        String sqlQuery = "" +
                "SELECT " +
                "    COUNT(DISTINCT (e.id)) as count " +
                "FROM " +
                "       entity e " +
                "   INNER JOIN " +
                "       score s " +
                "   ON " +
                "       e.id = s.entity_id " +
                "   INNER JOIN " +
                "       board b " +
                "   ON " +
                "       e.board_id = b.id " +
                "WHERE " +
                "   b.status = 'ACTIVE' " +
                "   AND " +
                "   b.id = ? " +
                "   AND " +
                "   e.status='ACTIVE' " +
                "   AND " +
                "   s.status='ACTIVE'";

        int count;
        try {
            count = jdbcTemplate.queryForObject(
                    sqlQuery,
                    new Object[]{boardId},
                    (rs, rowNum) -> rs.getInt("count")
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get entity info", e);
            throw new ResourceNotFoundException();
        }

        return new PaginatedResult(count, getEntityWithPointsByBoardId(boardId, limit, offset));
    }

    public List<EntityWithPoints> getEntityWithPointsByBoardId(int boardId, int limit, int offset) {
        String sqlQuery = "" +
                "SELECT " +
                "   e.*, " +
                "   SUM(s.points) as points, " +
                "   RANK() " +
                "       OVER (" +
                "           ORDER BY SUM(s.points) DESC " +
                "       ) `rank` " +
                "FROM " +
                "       entity e " +
                "   INNER JOIN " +
                "       score s " +
                "   ON " +
                "       e.id = s.entity_id " +
                "   INNER JOIN " +
                "       board b " +
                "   ON " +
                "       e.board_id = b.id " +
                "WHERE " +
                "   b.status = 'ACTIVE' " +
                "   AND " +
                "   b.id = ? " +
                "   AND " +
                "   e.status='ACTIVE' " +
                "   AND " +
                "   s.status='ACTIVE' " +
                "GROUP BY " +
                "   entity_id " +
                "ORDER BY " +
                "   points DESC " +
                "LIMIT " +
                "   ?, ?";

        try {
            return jdbcTemplate.query(
                    sqlQuery,
                    new Object[]{boardId, offset, limit},
                    (rs, rowNum) -> BeanUtil.getEntityWithPointsFromResultSet(rs)
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get entity info", e);
        }
        return null;
    }
}
