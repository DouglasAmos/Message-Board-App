package com.techelevator.dao;

import com.techelevator.model.Comment;
import jdk.jfr.Experimental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCommentDao implements CommentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Comment> getAllCommentsByPostId(int postId) {

        List<Comment> comments = new ArrayList<>();

        String sql = "SELECT comments.comment_id, post_id, comments.user_id, body, post_date, is_active, reply_id, SUM(vote) AS votes\n" +
                "FROM comments JOIN vote_comment ON comments.comment_id = vote_comment.comment_id\n" +
                "WHERE post_id = ? AND reply_id is not NULL " +
                "GROUP BY comments.comment_id;";

        try {

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, postId);
            while (results.next()) {
                Comment comment = mapRowToComment(results);
                comments.add(comment);

            }

        } catch (Exception ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }


        return comments;

    }

    @Override
    public Comment createComment(Comment comment) {

        String sql = "INSERT INTO comments(post_id, user_id, body, post_date, is_active, reply_id) " +
                "VALUES (?, ?, ?, ?, ?, ?) " +
                "RETURNING comment_id;";
        String sqlVoteComment = "INSERT INTO vote_comment(user_id, comment_id, vote) " +
                "VALUES (?, ?, ?);";

        try {

            int commentId = jdbcTemplate.queryForObject(sql, int.class, comment.getPostId(), comment.getUserId(), comment.getBody(), comment.getPostDate(), true, comment.getReplyId());
            comment.setCommentId(commentId);
            comment.setVotes(1);
            jdbcTemplate.update(sqlVoteComment, comment.getUserId(), commentId, 1);
        } catch (Exception ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }

        return comment;

    }
@Override
    public void updateCommentStatus(int commentId){
        String sql = "UPDATE comments SET is_active = false WHERE comment_id = ?;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, commentId);
        } catch (Exception ex){
            System.out.println("Something went wrong");
        }
    }


    private Comment mapRowToComment(SqlRowSet results) {
        Comment comment = new Comment();
        comment.setCommentId(results.getInt("comment_id"));
        comment.setPostId(results.getInt("post_id"));
        comment.setUserId(results.getInt("user_id"));
        if(!results.getBoolean("is_active")){
            comment.setBody("[deleted]");
        } else {
            comment.setBody(results.getString("body"));
        }
        if (results.getTimestamp("post_date") != null) {
            comment.setPostDate(results.getTimestamp("post_date").toLocalDateTime());
        }
        if(results.getBoolean("is_active")){
            comment.setActive(results.getBoolean("is_active"));
        }
        comment.setReplyId(results.getInt("reply_id"));
        comment.setVotes(results.getInt("votes"));

        return comment;
    }


}

