package com.techelevator.model;

public class Forum {
    private int forumId;
    private String title;
    private int userId;

    public Forum() {}

    public Forum(int forumId, String title, int userId) {
        this.forumId = forumId;
        this.title = title;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
