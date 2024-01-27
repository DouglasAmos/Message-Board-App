package com.techelevator.controller;

import com.techelevator.dao.PostDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Post;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path="/posts")
@CrossOrigin
@PreAuthorize("permitAll()")
public class PostController {

    @Autowired
    private PostDao postDao;
    @Autowired
    private UserDao userDao;

    @GetMapping(path="")
    public List<Post> getAllPostsByForumId(@RequestParam int forumId) {
        List<Post> posts;
        try {
            posts = postDao.getAllPostsByForumId(forumId);
            for(int i = 0; i < posts.size(); i++){
                int userId = posts.get(i).getUserId();
                posts.get(i).setUsername(userDao.getUserById(userId).getUsername());
            }
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong.");
        }
        return posts;
    }

    @GetMapping(path="/{id}")
    public Post getPostById(@PathVariable int id) {
        Post post = null;

        try {
            post = postDao.getPostById(id);
            post.setUsername(userDao.getUserById(post.getUserId()).getUsername());
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong.");
        }
        return post;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path="")
    @PreAuthorize("isAuthenticated()")
    public Post createPost(@RequestBody @Valid Post post, Principal principal) {
        Post newPost = null;
        post.setPostDate(LocalDateTime.now());
        String username = principal.getName();
        User user = userDao.getUserByUsername(username);
        int userId = user.getId();
        post.setUserId(userId);
        try {
            newPost = postDao.createPost(post);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong.");
        }
        newPost.setUsername(username);
        return newPost;
    }

    @RequestMapping(path="/popular")
    public List<Post> getPopularPosts(){
        List<Post> posts = null;
        try {
            posts = postDao.getPopularPosts();
            for(int i = 0; i < posts.size(); i++){
                int userId = posts.get(i).getUserId();
                posts.get(i).setUsername(userDao.getUserById(userId).getUsername());
            }
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong.");
        }
        return posts;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path="/{id}/remove")
    @PreAuthorize("isAuthenticated()")
    void updatePostStatus(@PathVariable int id) {
        try {
            postDao.updatePostStatus(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong.");
        }
    }
}
