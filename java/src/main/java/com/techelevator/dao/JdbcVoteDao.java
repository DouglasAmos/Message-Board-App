package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcVoteDao implements VoteDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public Vote getVote(int userId, int commentId) {

        Vote vote =  null;
        String sql = "SELECT * FROM vote_comment " +
                "WHERE user_id = ? AND comment_id = ?;";

        try {

            SqlRowSet rowset = jdbcTemplate.queryForRowSet(sql, userId, commentId);

            while (rowset.next()) {
                vote = mapRowToVote(rowset);
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return vote;

    }

    @Override
    public Vote createVote(Vote vote) {

        String sql = "INSERT INTO vote_comment(user_id, comment_id, vote) " +
                "VALUES(?, ?, ?);";

        try {

            int rowsAffected = jdbcTemplate.update(sql, vote.getUserId(), vote.getCommentId(), vote.getVote());

            if (rowsAffected == 0 || rowsAffected > 1) {
                throw new DaoException();
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return vote;

    }

//    @Override
//    public void deleteVote(int userId, int commentId) {
//        String sql = "DELETE FROM vote_comment " +
//                "WHERE user_id = ? AND comment_id = ?;";
//
//        try {
//
//            int rowsAffected = jdbcTemplate.update(sql, userId, commentId);
//
//            if (rowsAffected == 0 || rowsAffected > 1) {
//                throw new DaoException();
//            }
//
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Unable to connect to server or database", e);
//        } catch (DataIntegrityViolationException e) {
//            throw new DaoException("Data integrity violation", e);
//        }
//    }

    @Override
    public Vote updateVote(Vote vote) {
        String sql = "UPDATE vote_comment SET user_id = ?, comment_id = ?, vote = ? " +
                "WHERE user_id = ? AND comment_id = ?;";

        try {

            int rowsAffected = jdbcTemplate.update(sql, vote.getUserId(), vote.getCommentId(), vote.getVote(), vote.getUserId(), vote.getCommentId());

            if (rowsAffected == 0 || rowsAffected > 1) {
                throw new DaoException();
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return vote;
    }

    private Vote mapRowToVote(SqlRowSet rs) {
        Vote vote = new Vote();

        vote.setUserId(rs.getInt("user_id"));
        vote.setCommentId(rs.getInt("comment_id"));
        vote.setVote(rs.getInt("vote"));

        return vote;
    }
}
