package com.efgh.simplenetwork.service;

import com.efgh.simplenetwork.model.Network;
import com.efgh.simplenetwork.model.Post;
import com.efgh.simplenetwork.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class PostService {
    public Post addPost(String userHandle, Post post) {
        User user;
        try {
            user = Network.getUser(userHandle);
        } catch (NoSuchElementException e) {
            user = Network.addUser(userHandle);
        }
        return user.addPost(post.getContent());
    }

    public void deletePostById(String userHandle, String uniqueId) {
        Network.getUser(userHandle).removePost(uniqueId);
    }

    public Post getPostById(String userHandle, String uniqueId) {
        return Network.getUser(userHandle).getPostById(uniqueId);
    }

    public ArrayList<Post> getPostsByUserHandle(String userHandle, boolean reverseOrder) {
        return Network.getUser(userHandle).getWall(reverseOrder);
    }
}
