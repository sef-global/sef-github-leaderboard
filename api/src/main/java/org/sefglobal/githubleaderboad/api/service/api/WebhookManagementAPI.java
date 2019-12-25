package org.sefglobal.githubleaderboad.api.service.api;

import java.util.Map;
import org.sefglobal.githubleaderboad.api.beans.Score;
import org.sefglobal.githubleaderboad.api.beans.User;
import org.sefglobal.githubleaderboad.api.dao.ScoreDAO;
import org.sefglobal.githubleaderboad.api.dao.UserDAO;
import org.sefglobal.githubleaderboad.api.exception.BadRequestException;
import org.sefglobal.githubleaderboad.api.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookManagementAPI {

    private Logger logger = LoggerFactory.getLogger(WebhookManagementAPI.class);

    @Autowired
    private ScoreDAO scoreDAO;
    @Autowired
    private UserDAO userDAO;

    @PostMapping("/webhook")
    public void getBoardLeaderBoard(@RequestBody Map<String, Object> payload) {
        // Check if the event related to pull request
        if(payload.containsKey("pull_request")){
            Map<String, Object> pullRequest = (Map<String, Object>) payload.get("pull_request");
            if(pullRequest.containsKey("merged") && (boolean)pullRequest.get("merged")){
                System.out.println("Merged");
                Map<String, Object> userObject = (Map<String, Object>) pullRequest.get("user");
                int userId = (Integer) userObject.get("id");
                if(userDAO.getUserById(userId) == null){
                    User user = new User();
                    user.setId(userId);
                    user.setUsername((String) userObject.get("login"));
                    user.setImage((String)userObject.get("avatar_url"));
                    user.setUrl((String) userObject.get("html_url"));
                    try {
                        userDAO.addUser(user);
                        // Create score object
                        Score score = new Score();
                        score.setUserId(userId);
                        score.setPrUrl((String) pullRequest.get("html_url"));
                        // Add score
                        scoreDAO.addScore(score);
                    } catch (BadRequestException | ResourceNotFoundException e) {
                        logger.error("Failed add score", e);
                    }
                }
             }
        }
    }

}
