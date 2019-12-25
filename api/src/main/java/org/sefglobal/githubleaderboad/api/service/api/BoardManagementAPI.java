package org.sefglobal.githubleaderboad.api.service.api;

import org.sefglobal.githubleaderboad.api.beans.Board;
import org.sefglobal.githubleaderboad.api.dao.BoardDAO;
import org.sefglobal.githubleaderboad.api.exception.GiraffeAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class BoardManagementAPI {
    @Autowired
    private BoardDAO boardDAO;

    @GetMapping("/boards")
    public List<Board> getAllBoards(){
        return boardDAO.getAllBoards();
    }

    @PostMapping("/boards")
    public Board addEvent(@RequestBody Board board) throws GiraffeAPIException {
        return boardDAO.addBoard(board);
    }

    @GetMapping("/boards/{id}")
    public Board getBoardById(@PathVariable int id) throws GiraffeAPIException {
        return boardDAO.getBoardById(id);
    }

    @DeleteMapping("/boards/{id}")
    public void removeBoard(@PathVariable int id, HttpServletResponse response) throws GiraffeAPIException {
        boardDAO.removeBoard(id, response);
    }
}
