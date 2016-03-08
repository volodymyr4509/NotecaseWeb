package com.volodymyr.notecase.dao;

import com.volodymyr.notecase.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by volodymyr on 19.02.16.
 */
public interface UserDAO {

    User getUserById(int id) throws SQLException;

    User getUserByAuthToken(String authToken) throws SQLException;

    User getUserByEmail(String email) throws SQLException;

    int addUser(User user) throws SQLException;

    int updateUser(User user) throws SQLException;

    /**
     * all users you trust and send them your data.
     * It is possible to send one directional invitation to allow parent/child relationship.
     * You can force someone to send invitation to you and receive all his data.
     * Because your children dont need to know when you buy condoms:) But you need.
     */
    List<User> getUserFriends(int userId) throws SQLException;

    /**
     * All users who trust you and send you their data
     */
    List<User> getUserConstituents(int userId) throws SQLException;

    boolean addUserFriend(int userId, int friendId) throws SQLException;

}
