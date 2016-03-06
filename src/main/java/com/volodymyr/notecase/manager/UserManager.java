package com.volodymyr.notecase.manager;

import com.volodymyr.notecase.entity.User;

import java.util.List;

/**
 * Created by volodymyr on 19.02.16.
 */
public interface UserManager {

    String addUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(int userId);

    List<User> getAllTrustedUsers(int userId);

    String authenticateUser(String idToken);

    User getUserByAuthToken(String authToken);
}
