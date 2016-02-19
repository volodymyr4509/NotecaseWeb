package com.volodymyr.notecase.controller;

import com.volodymyr.notecase.entity.User;
import com.volodymyr.notecase.manager.UserManager;
import com.volodymyr.notecase.manager.UserManagerImpl;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by volodymyr on 19.02.16.
 */

@RestController
@RequestMapping("/user")
public class UserController {
    private static Logger log = Logger.getLogger(UserController.class.getName());

    private UserManager userManager = new UserManagerImpl();

//    @RequestMapping(value = "/get/{categoryId}", method = RequestMethod.GET)
//    public User getUser(@PathVariable int userId){
//        log.info("Get User with id = " + userId);
//        return userManager.getUser(userId);
//    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean addUser(@RequestBody User user) {
        log.info("Add new User: " + user);
        return userManager.addUser(user);
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

    @RequestMapping(value = "/getall/{userId}", method = RequestMethod.GET)
    public List<User> getAllTrustedUsers(@PathVariable int userId) {
        log.info("Get all trusted users for user with id: " + userId);
        return userManager.getAllTrustedUsers(userId);
    }

    @RequestMapping(value = "/registeridtoken", method = RequestMethod.POST)
    public boolean registerIdToken(@RequestBody String idToken) {
        log.info("RegisterIdToken post request");
        return userManager.registerIdToken(idToken);
    }

}
