package com.techelevator.controller;

import com.techelevator.dao.CommentDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Comment;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("permitAll()")
public class CommentController {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserDao userDao;

    @RequestMapping(path = "/comments", method = RequestMethod.GET)
    public List<Comment> getAllCommentsByPostId(@RequestParam int postId) {
        List<Comment> comments;
        try{
            comments = commentDao.getAllCommentsByPostId(postId);
            for(int i = 0; i < comments.size(); i++){
                int userId = comments.get(i).getUserId();
                comments.get(i).setUsername(userDao.getUserById(userId).getUsername());
            }
        } catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }
       return comments;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/comments", method = RequestMethod.POST)
    public Comment createComment(@RequestBody Comment comment, Principal principal){
        Comment newComment = null;
        comment.setPostDate(LocalDateTime.now());
        comment.setActive(true);
        String username = principal.getName();
        User user = userDao.getUserByUsername(username);
        int userId = user.getId();
        comment.setUserId(userId);
        try {
            newComment = commentDao.createComment(comment);
        } catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }
        newComment.setUsername(username);
        return newComment;
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/comments/setActive", method = RequestMethod.PUT)
    public void updateCommentStatus(@RequestParam int commentId){
        try{
            commentDao.updateCommentStatus(commentId);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong");
        }
    }
}
