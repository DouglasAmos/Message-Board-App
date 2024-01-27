package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.UserDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping(path="/users")
public class UserController {

    @Autowired
    private UserDao userDao;
    @GetMapping(path="/{username}")
    public UserDisplay getDisplayUserByUsername(@PathVariable String username){
        UserDisplay user = null;
        try {
            user = userDao.getDisplayUserByUsername(username);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong.");
        }
        return user;
    }
}
