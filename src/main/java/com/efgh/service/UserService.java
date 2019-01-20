package com.efgh.service;

import com.efgh.model.Network;
import com.efgh.model.Post;
import com.efgh.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    public User addUser(User user) {
        Network.addUser(user.getHandle(), user.getDisplayName());
        return Network.getUser(user.getHandle());
    }

    public User updateUser(User user) {
        User userToUpdate = Network.getUser(user.getHandle());
        userToUpdate.setDisplayName(user.getDisplayName());
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
}
