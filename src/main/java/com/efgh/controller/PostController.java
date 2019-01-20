package com.efgh.controller;

import com.efgh.model.Post;
import com.efgh.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController()
@RequestMapping("/user/{userHandle}/post")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(method = RequestMethod.POST)
    public Post addPost(@PathVariable(value="userHandle") String userHandle, Post post){
        return postService.addPost(userHandle, post);
    }

    @RequestMapping(value = "/{uniqueId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT )
    public void deletePost(@PathVariable(value="userHandle") String userHandle, @PathVariable(value="uniqueId") String uniqueId){
        postService.deletePostById(userHandle, uniqueId);
    }

    @RequestMapping(value = "/{uniqueId}", method = RequestMethod.GET)
    public Post getPost(@PathVariable(value="userHandle") String userHandle, @PathVariable(value="uniqueId") String uniqueId){
        return postService.getPostById(userHandle, uniqueId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ArrayList<Post> getPostsForUser(@PathVariable(value="userHandle") String userHandle, @RequestParam(value = "reverseOrder", defaultValue = "true") boolean reverseOrder){
        return postService.getPostsByUserHandle(userHandle, reverseOrder);
    }

}
