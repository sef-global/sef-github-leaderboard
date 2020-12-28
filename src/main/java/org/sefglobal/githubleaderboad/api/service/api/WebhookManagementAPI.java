package org.sefglobal.githubleaderboad.api.service.api;

import java.util.Map;
import javax.annotation.PostConstruct;
import org.sefglobal.githubleaderboad.api.beans.Score;
import org.sefglobal.githubleaderboad.api.beans.User;
import org.sefglobal.githubleaderboad.api.dao.ScoreDAO;
import org.sefglobal.githubleaderboad.api.dao.UserDAO;
import org.sefglobal.githubleaderboad.api.exception.BadRequestException;
import org.sefglobal.githubleaderboad.api.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookManagementAPI {
    private Logger logger = LoggerFactory.getLogger(WebhookManagementAPI.class);
    private static Environment environment;

    @Autowired
    private ScoreDAO scoreDAO;
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {
        WebhookManagementAPI.environment = env;
    }

    @PostMapping("/webhook/{key}")
    public void getBoardLeaderBoard(
            @RequestBody Map<String, Object> payload,
            @PathVariable String key) {
        final String webhookKey = environment.getProperty("config.webhookKey");
        // Check if the webhook key matches
        if (!webhookKey.equals(key)) {
            return;
        }
        // Check if the event related to a closed pull request
        if (payload.containsKey("pull_request") && ((String)payload.get("action")).equals("closed")) {
            Map<String, Object> pullRequest = (Map<String, Object>) payload.get("pull_request");
            if (pullRequest.containsKey("merged") && (boolean) pullRequest.get("merged")) {
                Map<String, Object> userObject = (Map<String, Object>) pullRequest.get("user");
                int userId = (Integer) userObject.get("id");
                try {
                    if (userDAO.getUserById(userId) == null) {
                        User user = new User();
                        user.setId(userId);
                        user.setUsername((String) userObject.get("login"));
                        user.setImage((String) userObject.get("avatar_url"));
                        user.setUrl((String) userObject.get("html_url"));
                        // Add user to DB
                        userDAO.addUser(user);
                    }
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
