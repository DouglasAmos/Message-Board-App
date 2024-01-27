package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Moderator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcModeratorDao implements ModeratorDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public Moderator getModeratorById(int moderatorId, int forumId) {
        Moderator moderator = new Moderator();
        String sql = "SELECT moderator_id, forum_id\n" +
                "FROM forum_moderator\n" +
                "WHERE moderator_id = ? AND forum_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, moderatorId, forumId);
            while(results.next()){
                moderator.setModeratorId(results.getInt("moderator_id"));
                moderator.setForumId(results.getInt("forum_id"));
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return moderator;
    }

    @Override
    public List<Moderator> getModeratedForums(int userId) {
        List<Moderator> moderators = new ArrayList<>();
        String sql = "SELECT moderator_id, forum_moderator.forum_id, forums.title\n" +
                "FROM forum_moderator\n" +
                "JOIN forums ON forums.forum_id = forum_moderator.forum_id\n" +
                "WHERE moderator_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while(results.next()) {
                Moderator moderator = new Moderator();
                moderator.setModeratorId(results.getInt("moderator_id"));
                moderator.setForumId(results.getInt("forum_id"));
                moderator.setForumName(results.getString("title"));

                moderators.add(moderator);

            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return moderators;

    }

    @Override
    public Moderator createModerator(Moderator moderator) {
        String sql = "INSERT INTO forum_moderator(moderator_id, forum_id)\n" +
                "VALUES (?, ?);";

        try {
            int rowsAffected = jdbcTemplate.update(sql, moderator.getModeratorId(), moderator.getForumId());

            if (rowsAffected == 0 || rowsAffected > 1) {
                throw new DaoException();
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return moderator;

    }

    @Override
    public void deleteModerator(Moderator moderator) {

        String sql = "DELETE FROM forum_moderator " +
                "WHERE moderator_id = ? AND forum_id = ?";
        try {

            int rowsAffected = jdbcTemplate.update(sql, moderator.getModeratorId(), moderator.getForumId());

            if (rowsAffected == 0 || rowsAffected > 1) {
                throw new DaoException();
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }



    }
}
