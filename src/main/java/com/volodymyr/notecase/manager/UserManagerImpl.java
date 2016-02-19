package com.volodymyr.notecase.manager;

import com.volodymyr.notecase.entity.User;

import java.util.List;

/**
 * Created by volodymyr on 19.02.16.
 */
public class UserManagerImpl implements UserManager {


    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(int userId) {
        return false;
    }

    @Override
    public List<User> getAllTrustedUsers(int userId) {
        return null;
    }

    @Override
    public boolean registerIdToken(String idToken) {
        return false;
    }
}


