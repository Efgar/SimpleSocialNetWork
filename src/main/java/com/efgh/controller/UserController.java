package com.efgh.controller;

import com.efgh.model.Post;
import com.efgh.model.User;
import com.efgh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public User addUser(User user) {
        return userService.addUser(user);
    }

    @RequestMapping(value = "/{userHandle}", method = RequestMethod.PUT)
    public User updateUser(User user) {
        return userService.updateUser(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/{userHandle}", method = RequestMethod.GET)
    public User getUserById(@PathVariable(value = "userHandle") String userHandle) {
        return userService.getUserByHandle(userHandle);
    }

    @RequestMapping(value = "/{userHandle}/timeline", method = RequestMethod.GET)
    public List<Post> getUserTimeline(@PathVariable(value = "userHandle") String userHandle, @RequestParam(value = "reverseOrder", defaultValue = "true") boolean reverseOrder) {
        return userService.getUserTimeLine(userHandle, reverseOrder);
    }
}
