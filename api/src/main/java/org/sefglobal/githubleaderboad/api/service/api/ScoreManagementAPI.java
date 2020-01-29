package org.sefglobal.githubleaderboad.api.service.api;

import org.sefglobal.githubleaderboad.api.beans.PaginatedResult;
import org.sefglobal.githubleaderboad.api.dao.ScoreDAO;
import org.sefglobal.githubleaderboad.api.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScoreManagementAPI {
    @Autowired
    private ScoreDAO scoreDAO;

    @GetMapping("/users/{userId}/scores")
    public PaginatedResult getAllScores(
            @PathVariable int userId,
            @RequestParam int limit,
            @RequestParam int offset,
            @RequestParam(value = "from", required=false) Long from,
            @RequestParam(value = "to", required=false) Long to
    ) throws ResourceNotFoundException {
        return scoreDAO.getPaginatedScoreByEntityId(userId, limit, offset, from, to);
    }

    @GetMapping("/scores")
    public PaginatedResult getBoardLeaderBoard(
            @RequestParam int limit,
            @RequestParam int offset,
            @RequestParam(value = "from", required=false) Long from,
            @RequestParam(value = "to", required=false) Long to
    ) throws ResourceNotFoundException {
        return scoreDAO.getPaginatedUsersWithPoints(limit, offset, from, to);
    }

}
