package org.sefglobal.githubleaderboad.api.service.api;

import org.sefglobal.githubleaderboad.api.beans.PaginatedResult;
import org.sefglobal.githubleaderboad.api.beans.Score;
import org.sefglobal.githubleaderboad.api.dao.ScoreDAO;
import org.sefglobal.githubleaderboad.api.exception.GiraffeAPIException;
import org.sefglobal.githubleaderboad.api.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ScoreManagementAPI {
    @Autowired
    private ScoreDAO scoreDAO;

    @GetMapping("/users/{userId}/scores")
    public PaginatedResult getAllScores(
            @PathVariable int userId,
            @RequestParam int limit,
            @RequestParam int offset
    ) throws ResourceNotFoundException {
        return scoreDAO.getPaginatedScoreByEntityId(userId, limit, offset);
    }

    @GetMapping("/scores")
    public PaginatedResult getBoardLeaderBoard(
            @RequestParam int limit,
            @RequestParam int offset
    ) throws ResourceNotFoundException {
        return scoreDAO.getPaginatedUsersWithPoints(limit,offset);
    }

}
