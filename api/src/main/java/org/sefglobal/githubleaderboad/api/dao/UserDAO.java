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

    public User addUser(User user) throws BadRequestException{
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
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlQuery);
                ps.setInt(1, user.getId());
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getImage());
                ps.setString(4, user.getUrl());
                return ps;
            });
            return getUserById(user.getId());
        } catch (DataAccessException e) {
            throw new BadRequestException("Unable to add user");
        }
    }

    public User getUserById(int id) {
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
            return null;
        }
    }

}
