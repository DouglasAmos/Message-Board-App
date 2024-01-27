package com.techelevator.dao;

import com.techelevator.model.Moderator;

import java.util.List;

public interface ModeratorDao {
    Moderator getModeratorById(int moderatorId, int forumId);
    List<Moderator> getModeratedForums(int userId);
    Moderator createModerator(Moderator moderator);
    void deleteModerator(Moderator moderator);

}
