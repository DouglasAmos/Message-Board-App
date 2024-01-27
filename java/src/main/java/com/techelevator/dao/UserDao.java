package com.techelevator.dao;

import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.User;
import com.techelevator.model.UserDisplay;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUserById(int id);

    User getUserByUsername(String username);
    UserDisplay getDisplayUserByUsername(String username);

    User createUser(RegisterUserDto user);
}
