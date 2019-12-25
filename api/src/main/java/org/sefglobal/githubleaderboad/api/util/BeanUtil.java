package org.sefglobal.githubleaderboad.api.util;

import org.sefglobal.githubleaderboad.api.beans.User;
import org.sefglobal.githubleaderboad.api.beans.UserWithPoints;
import org.sefglobal.githubleaderboad.api.beans.Score;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BeanUtil {
    public static User getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setImage(resultSet.getString("image"));

        return user;
    }

    public static Score getScoreFromResultSet(ResultSet resultSet) throws SQLException {
        Score score = new Score();

        score.setId(resultSet.getInt("id"));
        score.setUserId(resultSet.getInt("user_id"));
        score.setPrUrl(resultSet.getString("pr_url"));
        score.setCreatedAt(resultSet.getInt("created_at"));

        return score;
    }

    public static UserWithPoints getEntityWithPointsFromResultSet(ResultSet resultSet) throws SQLException {
        UserWithPoints entityWithPoints = new UserWithPoints();

        entityWithPoints.setId(resultSet.getInt("id"));
        entityWithPoints.setUsername(resultSet.getString("username"));
        entityWithPoints.setImage(resultSet.getString("image"));
        entityWithPoints.setUrl(resultSet.getString("url"));
        entityWithPoints.setPoints(resultSet.getInt("points") * 10);
        entityWithPoints.setRank(resultSet.getInt("rank"));

        return entityWithPoints;
    }


}
