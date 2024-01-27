package com.techelevator.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class Post {
    private int postId;
    @Min(value = 1)
    private int forumId;
    @NotBlank(message = "Title required.")
    private String title;
    private String url;
    private int headerCommentId;
    private int userId;
    private String username;
    @NotBlank(message = "Body required.")
    private String body;
    private LocalDateTime postDate;
    private int votes;

    private int upvotes;
    private int downvotes;

    private int engagement;
    private int replyId;
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public int getEngagement() {
        return engagement;
    }

    public void setEngagement(int engagement) {
        this.engagement = engagement;
    }

    public int getHeaderCommentId() {
        return headerCommentId;
    }

    public void setHeaderCommentId(int headerCommentId) {
        this.headerCommentId = headerCommentId;
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

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }
}
