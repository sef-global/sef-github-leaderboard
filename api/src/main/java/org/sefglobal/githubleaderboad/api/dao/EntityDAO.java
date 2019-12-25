package org.sefglobal.githubleaderboad.api.dao;

import org.sefglobal.githubleaderboad.api.beans.Entity;
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
import java.util.List;

@Repository
public class EntityDAO {

    private Logger logger = LoggerFactory.getLogger(EntityDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Entity> getAllEntities() {
        String sqlQuery = "" +
                "SELECT " +
                "   * " +
                "FROM " +
                "   entity " +
                "WHERE " +
                "   status = 'ACTIVE'";

        try {
            return jdbcTemplate.query(
                    sqlQuery,
                    (rs, rowNum) -> BeanUtil.getEntityFromResultSet(rs)
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get entity info", e);
        }
        return null;
    }

    public Entity addEntity(Entity entity) throws BadRequestException, ResourceNotFoundException {
        if (entity.getName() == null ||
                entity.getName().equals("") ||
                entity.getImage() == null ||
                entity.getImage().equals("")) {
            throw new BadRequestException("Bad Request");
        }

        String sqlQuery = "" +
                "INSERT INTO" +
                "   entity(" +
                "       name, " +
                "       image, " +
                "       board_id, " +
                "       status" +
                "   ) " +
                "VALUES " +
                "   (?,?,?,'ACTIVE')";

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"id"});
                ps.setString(1, entity.getName());
                ps.setString(2, entity.getImage());
                ps.setInt(3, entity.getBoardId());
                return ps;
            }, keyHolder);
            int key = keyHolder.getKey().intValue();
            return getEntityById(key);
        } catch (DataAccessException e) {
            logger.error("Unable to get Entity info", e);
        }
        return null;
    }

    public Entity getEntityById(int id) throws ResourceNotFoundException {
        String sqlQuery = "" +
                "SELECT " +
                "   * " +
                "FROM " +
                "   entity " +
                "WHERE " +
                "   id=? " +
                "   AND " +
                "   status='ACTIVE'";

        try {
            return jdbcTemplate.queryForObject(
                    sqlQuery,
                    new Object[]{id},
                    (rs, rowNum) -> BeanUtil.getEntityFromResultSet(rs)
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get info of '" + id + "'", e);
            throw new ResourceNotFoundException("Entity not found");
        }
    }

    public void removeEntity(int id, HttpServletResponse response) throws ResourceNotFoundException {
        String sqlQuery = "" +
                "UPDATE " +
                "   entity " +
                "SET " +
                "   status = 'REMOVED' " +
                "WHERE " +
                "   id = ?";

        try {
            jdbcTemplate.update(sqlQuery, id);
        } catch (DataAccessException e) {
            logger.error("Unable remove '" + id + "'", e);
            throw new ResourceNotFoundException("Entity not found");
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
