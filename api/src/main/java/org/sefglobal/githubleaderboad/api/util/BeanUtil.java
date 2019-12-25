package org.sefglobal.githubleaderboad.api.util;

import org.sefglobal.githubleaderboad.api.beans.Board;
import org.sefglobal.githubleaderboad.api.beans.Entity;
import org.sefglobal.githubleaderboad.api.beans.EntityWithPoints;
import org.sefglobal.githubleaderboad.api.beans.Score;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BeanUtil {
    public static Board getBoardFromResultSet(ResultSet resultSet) throws SQLException {
        Board board = new Board();

        board.setId(resultSet.getInt("id"));
        board.setTitle(resultSet.getString("title"));
        board.setImage(resultSet.getString("image"));
        board.setDescription(resultSet.getString("description"));
        board.setStatus(resultSet.getString("status"));

        return board;
    }

    public static Entity getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        Entity entity = new Entity();

        entity.setId(resultSet.getInt("id"));
        entity.setName(resultSet.getString("name"));
        entity.setImage(resultSet.getString("image"));
        entity.setBoardId(resultSet.getInt("board_id"));
        entity.setStatus(resultSet.getString("status"));

        return entity;
    }

    public static Score getScoreFromResultSet(ResultSet resultSet) throws SQLException {
        Score score = new Score();

        score.setId(resultSet.getInt("id"));
        score.setEntityId(resultSet.getInt("entity_id"));
        score.setDescription(resultSet.getString("description"));
        score.setPoints(resultSet.getInt("points"));
        score.setCreatedAt(resultSet.getInt("created_at"));
        score.setStatus(resultSet.getString("status"));

        return score;
    }

    public static EntityWithPoints getEntityWithPointsFromResultSet(ResultSet resultSet) throws SQLException {
        EntityWithPoints entityWithPoints = new EntityWithPoints();

        entityWithPoints.setId(resultSet.getInt("id"));
        entityWithPoints.setName(resultSet.getString("name"));
        entityWithPoints.setImage(resultSet.getString("image"));
        entityWithPoints.setBoardId(resultSet.getInt("board_id"));
        entityWithPoints.setStatus(resultSet.getString("status"));
        entityWithPoints.setPoints(resultSet.getInt("points"));
        entityWithPoints.setRank(resultSet.getInt("rank"));

        return entityWithPoints;
    }


}
