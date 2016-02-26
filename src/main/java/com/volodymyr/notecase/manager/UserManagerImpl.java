package com.volodymyr.notecase.manager;

import com.volodymyr.notecase.dao.UserDAO;
import com.volodymyr.notecase.dao.UserDAOImpl;
import com.volodymyr.notecase.entity.User;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by volodymyr on 19.02.16.
 */
public class UserManagerImpl implements UserManager {
    private static Logger log = Logger.getLogger(UserManagerImpl.class.getName());

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public int addUser(User user) {

        int id = -1;
        try {
            id = userDAO.addUser(user);
            log.info("User added: " + user);
        }catch (Exception e){
            log.error("Cannot add User: " + e);
        }
        return id;
    }

    @Override
    public boolean updateUser(User user) {
        boolean success = true;
        try {
            userDAO.updateUser(user);
            log.info("Updated User: " + user);
        }catch (Exception e){
            log.error("Cannot update User: " + user);
            success = false;
        }
        return success;
    }

    @Override
    public boolean deleteUser(int userId) {
        boolean success = false;
        try {
            User user = userDAO.getUserById(userId);
            if (user != null){
                user.setEnabled(false);
                userDAO.updateUser(user);
                log.info("User deleted successfully");
                success = true;
            }
        }catch (Exception e){
            log.error("Cannot delete User with id: " + userId, e);
        }
        return success;
    }

    @Override
    public List<User> getAllTrustedUsers(int userId) {
        List<User> userList = null;
        try {
            userList = userDAO.getAllTrustedUsers(userId);
            log.info("User list retrieved frm database");
        }catch (Exception e){
            log.error("Cannot retrieve user list by userId: " + userId , e);
        }
        return userList;
    }

    @Override
    public boolean registerIdToken(String idToken) {
        return false;
    }
}

