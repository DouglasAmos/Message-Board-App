package com.techelevator.controller;

import com.techelevator.dao.ForumDao;
import com.techelevator.dao.JdbcForumDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Forum;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("permitAll")
public class ForumController {
    @Autowired
    private ForumDao forumDao;
    @Autowired
    private UserDao userDao;
    @RequestMapping(path = "/forums", method = RequestMethod.GET)
    public List<Forum> getForums(){
        List<Forum> forums = new ArrayList<>();
        try {
            forums = forumDao.getAllForums();
        } catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }
        return forumDao.getAllForums();
    }
    @RequestMapping(path = "/forums/{forumId}")
    public Forum getForumById(@PathVariable int forumId){
        Forum forum = null;
        try {
            forum = forumDao.getForumById(forumId);
        } catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }
        return forum;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/forums", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public Forum createForum(@RequestBody Forum forum, Principal principal){
        Forum newForum = null;
        String username = principal.getName();
        User user = userDao.getUserByUsername(username);
        int userId = user.getId();
        try {
            newForum = forumDao.createForum(forum.getTitle(), userId);
        } catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }
        return newForum;
    }

    @RequestMapping(path = "/forums/favorites", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public List<Forum> getAllFavoritedForumsByUserId(@RequestParam String username){
        List<Forum> forums = new ArrayList<>();
        User user = userDao.getUserByUsername(username);
        int userId = user.getId();
        try {
            forums = forumDao.getAllFavoritedForumsByUserId(userId);
        } catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }
        return forums;
    }

    @GetMapping(path="/forums/active")
    public List<Forum> getActiveForums() {
        List<Forum> forums;
        try {
            forums = forumDao.getActiveForums();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong");
        }
        return forums;
    }

}
