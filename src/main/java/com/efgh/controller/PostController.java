package com.efgh.controller;

import com.efgh.model.Post;
import com.efgh.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v1.0/user/{userHandle}/post")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * Add a post on behalf of the specified user with the given text
     *
     * @param userHandle user whose post will be retrieved
     * @param post Post to be added
     * @return Added post object
     *
     * @throws IllegalArgumentException if the handle is empty or null
     * @throws NoSuchElementException if the provided handle does not correspond to any user on the system
     */
    @RequestMapping(method = RequestMethod.POST)
    public Post addPost(@PathVariable(value="userHandle") String userHandle, Post post){
        return postService.addPost(userHandle, post);
    }

    /**
     * Get the compilation of all the posts made by the user.
     *
     * @param userHandle user whose post will be retrieved
     * @param reverseOrder false to retrieve the posts in chronological order, true to retrieve
     *                     them in inverse chronological order, defaults to true
     * @return List containing the posts, sorted according what is specified
     *
     * @throws IllegalArgumentException if the handle is empty or null
     * @throws NoSuchElementException if the provided handle does not correspond to any user on the system
     */
    @RequestMapping(method = RequestMethod.GET)
    public ArrayList<Post> getPostsForUser(@PathVariable(value="userHandle") String userHandle, @RequestParam(value = "reverseOrder", defaultValue = "true") boolean reverseOrder){
        return postService.getPostsByUserHandle(userHandle, reverseOrder);
    }

    /**
     * Delete a specified post given its unique Id
     *
     * @param userHandle user whose post will be retrieved
     * @param uniqueId id of the post to be deleted
     *
     * @throws IllegalArgumentException if the handle is empty or null
     * @throws NoSuchElementException if the provided handle does not correspond to any user on the system or the post id is not present in the system
     */
    @RequestMapping(value = "/{uniqueId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT )
    public void deletePost(@PathVariable(value="userHandle") String userHandle, @PathVariable(value="uniqueId") String uniqueId){
        postService.deletePostById(userHandle, uniqueId);
    }

    /**
     * Retrieves a post given a specified ID
     *
     * @param userHandle user whose post will be retrieved
     * @param uniqueId id of the post to be retrieved
     * @return The correspondent post object
     *
     * @throws IllegalArgumentException if the handle is empty or null
     * @throws NoSuchElementException if the provided handle does not correspond to any user on the system or the post id is not present in the system
     */
    @RequestMapping(value = "/{uniqueId}", method = RequestMethod.GET)
    public Post getPost(@PathVariable(value="userHandle") String userHandle, @PathVariable(value="uniqueId") String uniqueId){
        return postService.getPostById(userHandle, uniqueId);
    }

}
