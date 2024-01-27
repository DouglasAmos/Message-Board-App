package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.FavoriteForum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sound.midi.Soundbank;
import java.util.Map;

@Component
public class JdbcFavoriteForumDao implements FavoriteForumDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public FavoriteForum createFavoriteForum(int userId, int forumId){
        FavoriteForum favoriteForum = new FavoriteForum();


        String sql ="INSERT INTO favorite_forum (user_id, forum_id)\n" +
                "VALUES (?, ?) RETURNING user_id,forum_id;";
        try{
          Map<String, Object> results = jdbcTemplate.queryForMap(sql, userId, forumId);
          favoriteForum.setForumId(forumId);
          favoriteForum.setUserId(userId);

        } catch (Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
            ex.printStackTrace();
        }
        return favoriteForum;


    }
    @Override
    public void deleteFavoriteForum(int userId, int forumId){
        String sql = "DELETE from favorite_forum\n" +
                "WHERE user_id = ? AND forum_id = ?;";

        try{
            int rowsAffected = jdbcTemplate.update(sql, userId, forumId);
            if(rowsAffected == 0  || rowsAffected > 1){
                throw new DaoException();
            }

        } catch (Exception ex){
            System.out.println("Something went wrong" + ex.getMessage());
        }


    }
}
