package com.volodymyr.notecase.controller;

import com.volodymyr.notecase.entity.User;
import com.volodymyr.notecase.manager.UserManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by volodymyr on 19.02.16.
 */

@RestController
@RequestMapping("/user")
public class UserController {
    private static Logger log = Logger.getLogger(UserController.class.getName());
    public static final String AUTHENTICATION_TOKEN = "AuthToken";

    @Autowired
    private UserManager userManager;

//    @RequestMapping(value = "/get/{categoryId}", method = RequestMethod.GET)
//    public User getUser(@PathVariable int userId){
//        log.info("Get User with id = " + userId);
//        return userManager.getUser(userId);
//    }

    /**
     * for user's friends only
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean addUser(@RequestBody User user, @RequestHeader(UserController.AUTHENTICATION_TOKEN) String authToken) {
        log.info("Add new User: " + user);
        if (user == null) {
            return false;
        }
        return userManager.addUser(user.getEmail(), authToken);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public boolean updateUser(@RequestBody User user) {
        log.info("Update User with id = " + user.getId() + ", User: " + user);
        return userManager.updateUser(user);
    }

    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
    public boolean deleteUser(@PathVariable int userId) {
        log.info("Delete User with id = " + userId);
        return userManager.deleteUser(userId);
    }

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public List<User> getAllTrustedUsers(@RequestHeader(UserController.AUTHENTICATION_TOKEN) String authToken) {
        log.info("Get all trusted users for user");
        return userManager.getAllTrustedUsers(authToken);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public User authenticateUser(@RequestBody String idToken) {
        log.info("AuthenticateUser post request");
        return userManager.authenticateUser(idToken);
    }

}
