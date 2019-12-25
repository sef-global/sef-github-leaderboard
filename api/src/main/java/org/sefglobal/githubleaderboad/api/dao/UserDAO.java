package org.sefglobal.githubleaderboad.api.dao;

import org.sefglobal.githubleaderboad.api.beans.User;
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

@Repository
public class UserDAO {

    private Logger logger = LoggerFactory.getLogger(UserDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User addEntity(User user) throws BadRequestException, ResourceNotFoundException {
        if (user.getUsername() == null ||
                user.getUsername().equals("") ||
                user.getImage() == null ||
                user.getImage().equals("")) {
            throw new BadRequestException("Bad Request");
        }

        String sqlQuery = "" +
                "INSERT INTO" +
                "   user(" +
                "       id, " +
                "       username, " +
                "       image, " +
                "       url " +
                "   ) " +
                "VALUES " +
                "   (?,?,?,?)";

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[] {"id"});
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getImage());
                ps.setString(3, user.getUrl());
                return ps;
            }, keyHolder);
            int key = keyHolder.getKey().intValue();
            return getEntityById(key);
        } catch (DataAccessException e) {
            logger.error("Unable add user", e);
        }
        return null;
    }

    public User getEntityById(int id) throws ResourceNotFoundException {
        String sqlQuery = "" +
                "SELECT " +
                "   * " +
                "FROM " +
                "   user " +
                "WHERE " +
                "   id=? ";
        try {
            return jdbcTemplate.queryForObject(
                    sqlQuery,
                    new Object[] {id},
                    (rs, rowNum) -> BeanUtil.getEntityFromResultSet(rs)
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get info of '" + id + "'", e);
            throw new ResourceNotFoundException("User not found");
        }
    }

}
