package com.techelevator.dao;

import com.techelevator.model.Forum;

import java.util.List;

public interface ForumDao {

    List<Forum> getAllForums();
    Forum getForumById(int id);
    Forum createForum(String title, int userId);
    List<Forum> getAllFavoritedForumsByUserId(int userId);
    List<Forum> getActiveForums();
}
