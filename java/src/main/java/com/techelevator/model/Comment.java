package com.techelevator.model;

import java.time.LocalDateTime;

public class Comment {

    private int commentId;
    private int postId;
    private int userId;
    private String username;
    private String body;
    private LocalDateTime postDate;
    private int replyId = 0;
    private int votes;
    private boolean isActive;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean status) {
        this.isActive = status;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
}
