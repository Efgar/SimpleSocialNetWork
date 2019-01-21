package com.efgh.service;

import com.efgh.model.Network;
import com.efgh.model.Post;
import com.efgh.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    public User addUser(User user) {
        return Network.addUser(user);
    }

    public User updateUser(String userHandle, User user) {
        User userToUpdate = Network.getUser(userHandle);
        userToUpdate.copy(user);
        return userToUpdate;
    }

    public List<User> getAllUsers() {
        return Network.getUsers().values().stream().sorted().collect(Collectors.toList());
    }

    public User getUserByHandle(String userHandle) {
        return Network.getUser(userHandle);
    }

    public List<Post> getUserTimeLine(String userHandle, boolean reverseOrder) {
        return Network.getUser(userHandle).getTimeLine(reverseOrder);
    }

    public Set<User> getFollowedUsers(String userHandle) {
        return Network.getUser(userHandle).getFollowers();
    }

    public Set<User> getFollowers(String userHandle) {
        return Network.getUser(userHandle).getFollowings();
    }

    public User addFollowedUser(String userHandle, User followedUser) {
        User user = Network.getUser(userHandle);
        followedUser = Network.getUser(followedUser.getHandle());
        Network.getUser(userHandle).follow(followedUser);
        return user;
    }

    public void removeFollowedUser(String userHandle, String followedUserHandle) {
        User followedUser = Network.getUser(followedUserHandle);
        Network.getUser(userHandle).unfollow(followedUser);
    }
}
