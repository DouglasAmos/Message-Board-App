package com.techelevator.dao;

import com.techelevator.model.Vote;

public interface VoteDao {

    Vote getVote(int userId, int commentId);
    Vote createVote(Vote vote);
//    void deleteVote(int userId, int commentId);
    Vote updateVote(Vote vote);


}