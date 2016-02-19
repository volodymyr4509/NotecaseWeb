package com.volodymyr.notecase.dao;

import com.volodymyr.notecase.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by volodymyr on 19.02.16.
 */
public interface UserDAO {

    int addUser(User user) throws SQLException;

    void updateUser(User user) throws SQLException;
    
    boolean deleteUser(int userId) throws SQLException;

    List<User> getAllTrustedUsers(int userId) throws SQLException;

    boolean registerIdToken(String idToken);

}
