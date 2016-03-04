package com.volodymyr.notecase.dao;

import com.volodymyr.notecase.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by volodymyr on 19.02.16.
 */
public interface UserDAO {

    User getUserById(int id) throws SQLException;

    int addUser(User user) throws SQLException;

    int updateUser(User user) throws SQLException;
    
    boolean deleteUser(int userId) throws SQLException;

    List<User> getAllTrustedUsers(int userId) throws SQLException;

    boolean registerIdToken(String idToken);

    User getUserByEmail(String email) throws SQLException;

}
