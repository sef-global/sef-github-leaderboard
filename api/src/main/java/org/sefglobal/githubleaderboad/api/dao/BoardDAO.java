package org.sefglobal.githubleaderboad.api.dao;

import org.sefglobal.githubleaderboad.api.beans.Board;
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
public class BoardDAO {

    private Logger logger = LoggerFactory.getLogger(BoardDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Board> getAllBoards() {
        String sqlQuery = "" +
                "SELECT " +
                "   * " +
                "FROM " +
                "   board " +
                "WHERE " +
                "   status = 'ACTIVE'";

        try {
            return jdbcTemplate.query(
                    sqlQuery,
                    (rs, rowNum) -> BeanUtil.getBoardFromResultSet(rs)
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get event info", e);
        }
        return null;
    }

    public Board addBoard(Board board) throws BadRequestException, ResourceNotFoundException {
        if (board.getTitle() == null ||
                board.getTitle().equals("") ||
                board.getImage() == null ||
                board.getImage().equals("")) {
            throw new BadRequestException("Bad Request");
        }

        String sqlQuery = "" +
                "INSERT INTO" +
                "   board(" +
                "       title, " +
                "       image, " +
                "       description, " +
                "       status" +
                "   ) " +
                "VALUES " +
                "   (?,?,?,'ACTIVE')";

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"id"});
                ps.setString(1, board.getTitle());
                ps.setString(2, board.getImage());
                ps.setString(3, board.getDescription());
                return ps;
            }, keyHolder);
            int key = keyHolder.getKey().intValue();
            return getBoardById(key);
        } catch (DataAccessException e) {
            logger.error("Unable to get Board info", e);
        }
        return null;
    }

    public Board getBoardById(int id) throws ResourceNotFoundException {
        String sqlQuery = "" +
                "SELECT " +
                "   * " +
                "FROM " +
                "   board " +
                "WHERE " +
                "   id=? " +
                "   AND " +
                "   status='ACTIVE'";

        try {
            return jdbcTemplate.queryForObject(
                    sqlQuery,
                    new Object[]{id},
                    (rs, rowNum) -> BeanUtil.getBoardFromResultSet(rs)
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get info of '" + id + "'", e);
            throw new ResourceNotFoundException("Board not found");
        }
    }

    public void removeBoard(int id, HttpServletResponse response) throws ResourceNotFoundException {
        String sqlQuery = "" +
                "UPDATE " +
                "   board " +
                "SET " +
                "   status = 'REMOVED' " +
                "WHERE " +
                "   id = ?";

        try {
            jdbcTemplate.update(sqlQuery, id);
        } catch (DataAccessException e) {
            logger.error("Unable remove '" + id + "'", e);
            throw new ResourceNotFoundException("Board not found");
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
