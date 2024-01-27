package com.techelevator.controller;

import com.techelevator.dao.ModeratorDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Moderator;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/moderators")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ModeratorController {
    @Autowired
    private ModeratorDao moderatorDao;
    @Autowired
    private UserDao userDao;

    @GetMapping(path = "")
    public Moderator getModeratorById(Principal principal, @RequestParam int forumId) {
        Moderator moderator = null;
        String username = principal.getName();
        User user = userDao.getUserByUsername(username);
        int userId = user.getId();
        try {
            moderator = moderatorDao.getModeratorById(userId, forumId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong.");
        }

        return moderator;

    }

    @GetMapping(path = "/forums")
    public List<Moderator> getAllModeratedForums(@RequestParam String username) {
        User user = userDao.getUserByUsername(username);
        int userId = user.getId();
        List<Moderator> moderators = null;
        try {
            moderators = moderatorDao.getModeratedForums(userId);


        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong.");
        }

        return moderators;

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public Moderator createModerator(@RequestBody @Valid Moderator moderator) {


        if (moderatorDao.getModeratorById(moderator.getModeratorId(), moderator.getForumId()) != null) {
            try {
                moderatorDao.createModerator(moderator);

            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong.");
            }

        } else {
            System.out.println("Non-moderator cannot promote user to moderator.");
        }

        return moderatorDao.getModeratorById(moderator.getModeratorId(), moderator.getForumId());

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "")
    public void deleteModerator(@RequestBody @Valid Moderator moderator, Principal principal) {
        String username = principal.getName();
        User user = userDao.getUserByUsername(username);
        int userId = user.getId();
        try {
            if (moderatorDao.getModeratorById(userId, moderator.getForumId()) != null) {

                moderatorDao.deleteModerator(moderator);
            } else {
                System.out.println("Non-moderator cannot remove user to moderator.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong.");
        }


    }


}
