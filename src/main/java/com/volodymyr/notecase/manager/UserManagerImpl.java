package com.volodymyr.notecase.manager;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.volodymyr.notecase.dao.UserDAO;
import com.volodymyr.notecase.dao.UserDAOImpl;
import com.volodymyr.notecase.entity.User;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by volodymyr on 19.02.16.
 */
public class UserManagerImpl implements UserManager {
    private static Logger log = Logger.getLogger(UserManagerImpl.class.getName());
    private static final String SERVER_CLIENT_ID = "1062930196155-uno4j21qb2akk7eojrueek4f0evb89m5.apps.googleusercontent.com";
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public boolean addUser(String friendEmail, String authToken) {
        try {
            User deviceOwner = userDAO.getUserByAuthToken(authToken);
            User friend = userDAO.getUserByEmail(friendEmail);
            if (deviceOwner == null || friend == null) {
                return false;
            }
            List<User> friends = userDAO.getUserFriends(deviceOwner.getId());
            if (friends != null) {
                for (User u : friends) {
                    if (u.getId() == friend.getId()) {
                        return true;
                    }
                }
            }
            userDAO.addUserFriend(deviceOwner.getId(), friend.getId());

        } catch (SQLException e) {
            log.error("Cannot add Friend with email: " + friendEmail);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        boolean success = true;
        try {
            userDAO.updateUser(user);
            log.info("Updated User: " + user);
        } catch (Exception e) {
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
            if (user != null) {
                user.setEnabled(false);
                userDAO.updateUser(user);
                log.info("User deleted successfully");
                success = true;
            }
        } catch (Exception e) {
            log.error("Cannot delete User with id: " + userId, e);
        }
        return success;
    }

    @Override
    public List<User> getAllTrustedUsers(String authToken) {
        List<User> userList = null;
        try {
            User owner = userDAO.getUserByAuthToken(authToken);
            userList = userDAO.getUserFriends(owner.getId());
            log.info("User list retrieved frm database");
        } catch (Exception e) {
            log.error("Cannot retrieve user list by userId: " + authToken, e);
        }
        return userList;
    }
    @Override
    public User authenticateUser(String idToken) {
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Arrays.asList(SERVER_CLIENT_ID))
                .setIssuer("https://accounts.google.com")
                .build();
        try {
            GoogleIdToken googleIdToken = verifier.verify(idToken);
            if (googleIdToken != null && googleIdToken.getPayload() != null) {
                GoogleIdToken.Payload payload = googleIdToken.getPayload();
                User userFromIdToken = new User();
                userFromIdToken.setEmail(payload.getEmail());
                userFromIdToken.setAuthToken(UUID.randomUUID().toString());
                userFromIdToken.setName((String) payload.get("name"));

                User existingUser = userDAO.getUserByEmail(userFromIdToken.getEmail());
                if (existingUser == null) {
                    int id = userDAO.addUser(userFromIdToken);
                    userFromIdToken.setId(id);
                } else {
                    userFromIdToken.setAuthToken(existingUser.getAuthToken());
                    userFromIdToken.setId(existingUser.getId());
                    userFromIdToken.setLastUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
                    userFromIdToken.setEnabled(true);
                    userDAO.updateUser(userFromIdToken);
                }
                return userFromIdToken;
            } else {
                log.warn("User is not authenticated with current idToken");
            }
        } catch (Exception e) {
            log.error("Cannot authenticate user", e);
        }
        return null;
    }


    @Override
    public User getUserByAuthToken(String authToken) {
        try {
            return userDAO.getUserByAuthToken(authToken);
        } catch (Exception e) {
            log.error("Cannot get user by authToken: " + authToken, e);
        }
        return null;
    }
}

