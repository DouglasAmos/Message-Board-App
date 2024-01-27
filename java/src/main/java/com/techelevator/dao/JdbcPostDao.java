package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Post;
import com.techelevator.model.PostComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JdbcPostDao implements PostDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Post> getPopularPosts(){
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT posts.post_id, forum_id, title, url, comments.comment_id, comments.user_id, body, post_date, is_active, reply_id, SUM(vote_comment.vote) as votes " +
                "FROM posts " +
                "JOIN comments ON posts.post_id = comments.post_id " +
                "JOIN vote_comment ON comments.comment_id = vote_comment.comment_id " +
                "GROUP BY posts.post_id, comments.comment_id " +
                "ORDER BY comments.post_date DESC;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()){
                Post post = mapRowToPost(results,false);
                post.setEngagement(calcEngagement(post.getHeaderCommentId(), post.getPostId()));
                post.setReplyId(results.getInt("reply_id"));
                posts.add(post);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        List<Post> sortedPosts = sortPopularPosts(posts);
        return sortedPosts;
    }
    @Override
    public List<Post> getAllPostsByForumId(int id) {

        List<Post> posts = new ArrayList<>();
        String sql = "SELECT posts.post_id, forum_id, title, url, comments.comment_id, comments.user_id, body, post_date, is_active, reply_id, SUM(vote_comment.vote) as votes " +
                "FROM posts " +
                "JOIN comments ON posts.post_id = comments.post_id " +
                "JOIN vote_comment ON comments.comment_id = vote_comment.comment_id " +
                "WHERE forum_id = ? " +
                "AND reply_id is NULL " +
                "GROUP BY posts.post_id, comments.comment_id;";

        try {

            SqlRowSet rowset = jdbcTemplate.queryForRowSet(sql, id);
            while (rowset.next()) {
                Post post = mapRowToPost(rowset,false);
                post.setEngagement(calcEngagement(post.getHeaderCommentId(), post.getPostId()));
                posts.add(post);
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return posts;

    }

    @Override
    public Post getPostById(int id) {

        Post post = null;

        String sql = "SELECT posts.post_id, forum_id, title, url, comments.comment_id, comments.user_id, body, post_date, is_active, reply_id, SUM(vote_comment.vote) as votes, " +
                "COUNT(vote_comment.vote) FILTER (WHERE vote = 1) as upvotes, COUNT(vote_comment.vote) FILTER (WHERE vote = -1) as downvotes " +
                "FROM posts " +
                "JOIN comments ON posts.post_id = comments.post_id " +
                "JOIN vote_comment ON comments.comment_id = vote_comment.comment_id " +
                "WHERE posts.post_id = ? " +
                "AND reply_id is NULL " +
                "GROUP BY posts.post_id, comments.comment_id;";

        try {

            SqlRowSet rowset = jdbcTemplate.queryForRowSet(sql, id);
            if (rowset.next()) {
                post = mapRowToPost(rowset, true);
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return post;
    }

    @Override
    public void updatePostStatus(int id) {
        String sql = "UPDATE comments " +
                "SET is_active = false " +
                "WHERE post_id = ? AND reply_id is NULL;";
        try {
            jdbcTemplate.update(sql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public Post createPost(Post post) {
        String sql = "INSERT INTO posts(forum_id, title, url) " +
                "VALUES (?, ?, ?) " +
                "RETURNING post_id;";

        String sql2 = "INSERT INTO comments(post_id, user_id, body, post_date, is_active) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "RETURNING comment_id;";

        String sqlVoteComment = "INSERT INTO vote_comment(user_id, comment_id, vote) " +
                "VALUES (?, ?, ?);";

        try {

            int postId = jdbcTemplate.queryForObject(sql, int.class, post.getForumId(), post.getTitle(), post.getUrl());
            post.setPostId(postId);

            int commentId = jdbcTemplate.queryForObject(sql2, int.class, post.getPostId(), post.getUserId(), post.getBody(),post.getPostDate(), true);
            post.setHeaderCommentId(commentId);

            jdbcTemplate.update(sqlVoteComment, post.getUserId(), commentId, 1);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return post;
    }
    private int calcEngagement(int commentId, int postId){
        String sql = "SELECT COUNT(vote_comment.vote) FILTER (WHERE vote != 0 AND comment_id = ?) as votes FROM vote_comment;";
        String sql2 = "SELECT COUNT(comments.comment_id) FILTER (WHERE reply_id IS NOT NULL AND post_id = ?) from comments;";
        int engagement = 0;
        try{
            engagement = jdbcTemplate.queryForObject(sql, int.class, commentId);
            engagement += jdbcTemplate.queryForObject(sql2, int.class, postId);

        } catch (Exception ex){
            System.out.println("Something went wrong");
        }
        return engagement;
    }
    private List<Post> sortPopularPosts(List<Post> posts){
        List<Post> sortedPosts = new ArrayList<>();
        List<Post> replies = new ArrayList<>();
        List<Post> originalPosts = new ArrayList<>();
        List<Integer> addedPostIds = new ArrayList<>();
        for(int i = 0; i < posts.size();i++){
            if(posts.get(i).getReplyId() != 0){
                replies.add(posts.get(i));
            } else if (posts.get(i).getReplyId() == 0){
                originalPosts.add(posts.get(i));
            }
        }
        for(int i = 0; i < replies.size(); i++){
            long dateDiff = replies.get(i).getPostDate().until(LocalDateTime.now(), ChronoUnit.HOURS);
            if(posts.get(i).getPostDate().until(LocalDateTime.now(), ChronoUnit.HOURS) < 24){
                    for(int i2 = 0; i2 < originalPosts.size(); i2++){
                        if(originalPosts.get(i2).getPostId() == posts.get(i).getPostId() && !addedPostIds.contains(posts.get(i).getPostId())){
                            addedPostIds.add(posts.get(i).getPostId());
                            sortedPosts.add(originalPosts.get(i2));
                        }
                    }
            }
        }
        Collections.sort(sortedPosts, new PostComparator());
        return sortedPosts.stream().limit(10).collect(Collectors.toList());
    }

    private Post mapRowToPost(SqlRowSet rs, boolean isDetailView) {

        Post post = new Post();

        post.setPostId(rs.getInt("post_id"));
        post.setForumId(rs.getInt("forum_id"));
        if (rs.getBoolean("is_active")) {
            post.setBody(rs.getString("body"));
            post.setUrl(rs.getString("url"));
        } else {
            post.setBody("[deleted]");
        }
        post.setTitle(rs.getString("title"));
        post.setUserId(rs.getInt("user_id"));
        post.setHeaderCommentId(rs.getInt("comment_id"));
        if (rs.getTimestamp("post_date") != null) {
            post.setPostDate(rs.getTimestamp("post_date").toLocalDateTime());
        }
        post.setVotes(rs.getInt("votes"));
        if (isDetailView) {
            post.setUpvotes(rs.getInt("upvotes"));
            post.setDownvotes(rs.getInt("downvotes"));
        }
        post.setActive(rs.getBoolean("is_active"));
        return post;
    }

}
