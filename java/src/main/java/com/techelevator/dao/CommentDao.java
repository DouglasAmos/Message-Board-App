package com.techelevator.dao;

import com.techelevator.model.Comment;

import java.util.List;

public interface CommentDao {

    List<Comment> getAllCommentsByPostId(int postId);
    Comment createComment(Comment comment);
    void updateCommentStatus(int commentId);
}
