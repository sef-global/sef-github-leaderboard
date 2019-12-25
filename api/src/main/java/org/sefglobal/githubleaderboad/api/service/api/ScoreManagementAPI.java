package org.sefglobal.githubleaderboad.api.service.api;

import org.sefglobal.githubleaderboad.api.beans.PaginatedResult;
import org.sefglobal.githubleaderboad.api.beans.Score;
import org.sefglobal.githubleaderboad.api.dao.ScoreDAO;
import org.sefglobal.githubleaderboad.api.exception.GiraffeAPIException;
import org.sefglobal.githubleaderboad.api.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ScoreManagementAPI {
    @Autowired
    private ScoreDAO scoreDAO;

    @GetMapping("/entities/{entityId}/scores")
    public PaginatedResult getAllScores(
            @PathVariable int entityId,
            @RequestParam int limit,
            @RequestParam int offset
    ) throws ResourceNotFoundException {
        return scoreDAO.getPaginatedScoreByEntityId(entityId, limit, offset);
    }

    @GetMapping("boards/{boardId}/scores")
    public PaginatedResult getBoardLeaderBoard(
            @PathVariable int boardId,
            @RequestParam int limit,
            @RequestParam int offset
    ) throws ResourceNotFoundException {
        return scoreDAO.getPaginatedEntitiesWithPointsByBoardId(boardId,limit,offset);
    }

    @PostMapping("/entities/{entityId}/scores")
    public Score addScore(@RequestBody Score score, @PathVariable int entityId) throws GiraffeAPIException {
        score.setEntityId(entityId);
        return scoreDAO.addScore(score);
    }

    @DeleteMapping("/scores/{id}")
    public void removeScore(@PathVariable int id, HttpServletResponse response) throws GiraffeAPIException {
        scoreDAO.removeScore(id, response);
    }
}
