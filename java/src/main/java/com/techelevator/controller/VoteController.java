package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.dao.VoteDao;
import com.techelevator.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping(path="/votes")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@Component
public class VoteController {

    @Autowired
    private VoteDao voteDao;
    @Autowired
    private UserDao userDao;

    @GetMapping(path = "")
    public Vote getVote(Principal principal, @RequestParam int commentId) {

        Vote vote = null;
        int userId = userDao.getUserByUsername(principal.getName()).getId();

        try {
            vote = voteDao.getVote(userId, commentId);
        } catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }
        return vote;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public Vote createVote(Principal principal, @RequestBody Vote vote) {

        vote.setUserId(userDao.getUserByUsername(principal.getName()).getId());

        try {
            voteDao.createVote(vote);
        } catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }
        return vote;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(path = "")
    public Vote updateVote(Principal principal, @RequestBody Vote vote) {

        vote.setUserId(userDao.getUserByUsername(principal.getName()).getId());

        try {
            voteDao.updateVote(vote);
        } catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }
        return vote;
    }

//    @DeleteMapping(path = "")
//    public void deleteVote(Principal principal, @RequestParam int commentId) {
//
//        int userId = userDao.getUserByUsername(principal.getName()).getId();
//
//        try {
//            voteDao.deleteVote(userId, commentId);
//        } catch (Exception ex){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
//        }
//
//    }


}
