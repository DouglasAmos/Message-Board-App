package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Forum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcForumDao implements ForumDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Forum> getAllForums() {
        List<Forum> forums = new ArrayList<>();
        String sql = "SELECT forum_id, title, user_id FROM forums";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()){
                Forum forum = mapRowToForum(results);
                forums.add(forum);
            }
        } catch(Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
        }
        return forums;
    }

    @Override
    public Forum getForumById(int id) {
        Forum forum = null;
        String sql = "SELECT forum_id, title, user_id FROM forums " +
                "WHERE forum_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while(results.next()){
                forum = mapRowToForum(results);
            }
        } catch(Exception ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
        return forum;
    }

    @Override
    public Forum createForum(String title, int userId) {
        Forum forum = new Forum();
        String sql = "INSERT INTO forums (title, user_id) " +
                "VALUES(?,?) RETURNING forum_id;";
        String sql2 = "INSERT INTO forum_moderator (forum_id, moderator_id) " +
                "VALUES(?, ?) RETURNING moderator_id;";
        try{
            int forumId = jdbcTemplate.queryForObject(sql, int.class, title, userId);
            forum = getForumById(forumId);
            int moderatorId = jdbcTemplate.queryForObject(sql2, int.class, forumId, userId);
        } catch (Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
            ex.printStackTrace();
        }
        return forum;
    }

    @Override
    public List<Forum> getAllFavoritedForumsByUserId(int userId){
        List<Forum> forums = new ArrayList<>();
        String sql = "SELECT forum_id, title, user_id\n" +
                "FROM forums\n" +
                "WHERE forum_id in (SELECT forum_id\n" +
                "FROM favorite_forum\n" +
                "WHERE user_id = ?);";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while(results.next()){
                Forum forum = mapRowToForum(results);
                forums.add(forum);
            }
        } catch(Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
        }
        return forums;

    }

    @Override
    public List<Forum> getActiveForums() {
        List<Forum> forums = new ArrayList<>();
        String sql = "SELECT forums.forum_id, forums.title, forums.user_id\n" +
                "FROM forums\n" +
                "JOIN (\n" +
                "SELECT forum_id, MAX(comments.post_date) as max_date\n" +
                "FROM posts\n" +
                "JOIN comments ON comments.post_id = posts.post_id\n" +
                "WHERE comments.reply_id is NULL\n" +
                "GROUP BY forum_id\n" +
                ") p1 ON p1.forum_id = forums.forum_id\n" +
                "ORDER BY max_date DESC\n" +
                "LIMIT 5;";

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
            while (rowSet.next()) {
                forums.add(mapRowToForum(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return forums;
    }


    private Forum mapRowToForum(SqlRowSet rs){
        Forum forum = new Forum();
        forum.setForumId(rs.getInt("forum_id"));
        forum.setTitle(rs.getString("title"));
        forum.setUserId(rs.getInt("user_id"));
        return forum;
    }
}
