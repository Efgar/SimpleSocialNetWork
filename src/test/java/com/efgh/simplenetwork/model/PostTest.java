package com.efgh.simplenetwork.model;

import org.junit.Assert;
import org.junit.Test;

public class PostTest {

    @Test
    public void initializePost(){
        String postContent = "My new test " + java.util.UUID.randomUUID().toString();
        Post post = new Post(postContent);

        Assert.assertEquals(postContent, post.getContent());
        Assert.assertNotNull(post.getCreationDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void initializePost_nullContent(){
        new Post(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void initializePost_EmptyContent(){
        new Post("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void initializePost_LongContent(){
        StringBuilder newBuilder = new StringBuilder();
        for (int i = 0; i < 150; i++) {
            newBuilder.append(i);
        }
        new Post(newBuilder.toString());
    }

    @Test
    public void compare() throws InterruptedException {
        Post firstPost = new Post("First post");

        //Adding a sleep to simulate the creation time difference
        Thread.sleep(1);

        Post secondPost = new Post("Second post");

        Assert.assertTrue(firstPost.compareTo(secondPost) < 0);
        Assert.assertTrue(secondPost.compareTo(firstPost) > 0);
    }

    @Test
    public void compare_SameDate() {
        Post firstPost = new Post("First post");
        Post secondPost = new Post("Second post");

        Assert.assertTrue(firstPost.compareTo(secondPost) < 0);
    }
}
