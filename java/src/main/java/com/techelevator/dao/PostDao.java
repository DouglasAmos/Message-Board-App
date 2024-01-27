package com.techelevator.dao;

import com.techelevator.model.Post;

import java.util.List;

public interface PostDao {

    List<Post> getAllPostsByForumId(int id);
    Post getPostById(int id);
    Post createPost(Post post);
    List<Post> getPopularPosts();
    void updatePostStatus(int id);
}
