package com.techelevator.controller;

import com.techelevator.dao.FavoriteForumDao;
import com.techelevator.dao.ForumDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.FavoriteForum;
import com.techelevator.model.Forum;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@CrossOrigin
@PreAuthorize("permitAll")
public class FavoriteForumController {
    @Autowired
    private FavoriteForumDao FavoriteforumDao;
    @Autowired
    private UserDao userDao;
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/forums/favorite/{forumId}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public FavoriteForum createFavoriteForum(Principal principal, @PathVariable int forumId){
        FavoriteForum favoriteForum = null;
        String username = principal.getName();
        User user = userDao.getUserByUsername(username);
        int userId = user.getId();
        try {
            favoriteForum = FavoriteforumDao.createFavoriteForum(userId,forumId);
        } catch (Exception ex){
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");

        }
        return favoriteForum;

    }


    @RequestMapping(path = "/forums/favorite/{forumId}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated()")
    public void deleteFavoriteForum(Principal principal, @PathVariable int forumId){

        String username = principal.getName();
        User user = userDao.getUserByUsername(username);
        int userId = user.getId();
        try{

            FavoriteforumDao.deleteFavoriteForum(userId, forumId);

        } catch (Exception ex){
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");

        }



    }

}
