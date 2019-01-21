package com.efgh.simplenetwork.controller;

import com.efgh.simplenetwork.model.Post;
import com.efgh.simplenetwork.model.User;
import com.efgh.simplenetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/v1.0/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Creates a new User in the system, please note that at this point the complex objects update is not supported (i.e. followers, followed, posts)
     *
     * @param user user to be created
     * @return Created user
     *
     * @throws IllegalArgumentException if there is any inconsistency with the user object definition
     */
    @RequestMapping(method = RequestMethod.POST)
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * Returns the full list of users in the system
     * @return List containing the users in the system
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.getAllUsers();
    }


    /**
     * Modifies a specified user with the values provided in an object, please note that at this point the complex objects update is not supported (i.e. followers, followed, posts)
     *
     * @param userHandle user handle identifying the user to be modified
     * @param user user object containing the values to be updated
     * @return Modified user object
     *
     * @throws IllegalArgumentException if the handle is empty or null
     * @throws NoSuchElementException if the provided handle does not correspond to any user on the system
     */
    @RequestMapping(value = "/{userHandle}", method = RequestMethod.PUT)
    public User updateUser(@PathVariable(value = "userHandle") String userHandle, @RequestBody User user) {
        return userService.updateUser(userHandle, user);
    }

    /**
     * Retrieves a user details given its handle.
     *
     * @param userHandle user handle identifying the user to be retrieved
     * @return User object containing the requested user.
     * @throws IllegalArgumentException if the handle is empty or null
     * @throws NoSuchElementException   if the provided handle does not correspond to any user on the system
     */
    @RequestMapping(value = "/{userHandle}", method = RequestMethod.GET)
    public User getUserById(@PathVariable(value = "userHandle") String userHandle) {
        return userService.getUserByHandle(userHandle);
    }

    /**
     * Get the compilation of all the posts made by the users being followed.
     *
     * @param userHandle   user handle identifying the user whose posts are to be retrieved
     * @param reverseOrder false to retrieve the posts in chronological order, true to retrieve
     *                     them in inverse chronological order, defaults to true
     * @return List of posts sorted according to what was specified
     * @throws IllegalArgumentException if the handle is empty or null
     * @throws NoSuchElementException   if the provided handle does not correspond to any user on the system
     */
    @RequestMapping(value = "/{userHandle}/timeline", method = RequestMethod.GET)
    public List<Post> getUserTimeline(@PathVariable(value = "userHandle") String userHandle, @RequestParam(value = "reverseOrder", defaultValue = "true") boolean reverseOrder) {
        return userService.getUserTimeLine(userHandle, reverseOrder);
    }

    /**
     * Retrieves the list of users being followed by the user
     *
     * @param userHandle user handle identifying the user whose followed are to be retrieved
     * @return List of users being followed by the user
     * @throws IllegalArgumentException if the handle is empty or null
     * @throws NoSuchElementException   if the provided handle does not correspond to any user on the system
     */
    @RequestMapping(value = "/{userHandle}/followed", method = RequestMethod.GET)
    public Set<User> getFollowedUsers(@PathVariable(value = "userHandle") String userHandle) {
        return userService.getFollowedUsers(userHandle);
    }

    /**
     * Adds the received user to the followed list of the user specified in the url
     *
     * @param userHandle   user handle identifying the user whose followed list is to be modified
     * @param followedUser user to be followed, please note that the only attribute to be taking into account in the request is the user handle.
     * @return User object whose followed list was being modified
     * @throws IllegalArgumentException if the handle is empty or null
     * @throws NoSuchElementException   if the provided handle does not correspond to any user on the system or if the sent user to be followed does not match any user in the system
     */
    @RequestMapping(value = "/{userHandle}/followed", method = RequestMethod.POST)
    public User addFollowedUser(@PathVariable(value = "userHandle") String userHandle, @RequestBody User followedUser) {
        return userService.addFollowedUser(userHandle, followedUser);
    }

    /**
     * Remove a specified user from the followed list
     *
     * @param userHandle         User handle of the user whose followed list is going to be modified
     * @param followedUserHandle User handle of the user to be removed from the followed list
     * @throws IllegalArgumentException if the handle is empty or null
     * @throws NoSuchElementException   if the provided handle does not correspond to any user on the system
     */
    @RequestMapping(value = "/{userHandle}/followed/{followedUserHandle}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT )
    public void removeFollowedUser(@PathVariable(value = "userHandle") String userHandle, @PathVariable(value = "followedUserHandle") String followedUserHandle) {
        userService.removeFollowedUser(userHandle, followedUserHandle);
    }

    /**
     * Retrieve the list of followers for the specified user
     *
     * @param userHandle User whose followers are to be retrieved
     * @return List of users that are following the specified user
     * @throws IllegalArgumentException if the handle is empty or null
     * @throws NoSuchElementException   if the provided handle does not correspond to any user on the system
     */
    @RequestMapping(value = "/{userHandle}/followers", method = RequestMethod.GET)
    public Set<User> getFollowers(@PathVariable(value = "userHandle") String userHandle) {
        return userService.getFollowers(userHandle);
    }
}
