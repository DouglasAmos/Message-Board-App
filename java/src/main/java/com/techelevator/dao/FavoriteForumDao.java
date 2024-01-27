package com.techelevator.dao;

import com.techelevator.model.FavoriteForum;

public interface FavoriteForumDao {
    FavoriteForum createFavoriteForum(int userId, int forumId);
    void deleteFavoriteForum(int userId, int forumId);
}
