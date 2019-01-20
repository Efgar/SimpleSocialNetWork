package com.efgh.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;

public class UserTest {

    @Test
    public void initializeUser() {
        String newUserHandle = java.util.UUID.randomUUID().toString();
        User user = new User(newUserHandle);
        Assert.assertNotNull(user);
        Assert.assertEquals(newUserHandle, user.getHandle());
        Assert.assertEquals(0, user.getPosts().size());
        Assert.assertEquals(0, user.getFollowers().size());
        Assert.assertEquals(0, user.getFollowings().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void initializeUser_NullHandle() {
        new User(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void initializeUser_EmptyHandle() {
        new User("");
    }

    @Test
    public void initializeUserWithDisplayName() {
        String newUserHandle = java.util.UUID.randomUUID().toString();
        String newUserDisplayName = "I am " + java.util.UUID.randomUUID().toString();

        User user = new User(newUserHandle, newUserDisplayName);

        Assert.assertNotNull(user);
        Assert.assertEquals(newUserHandle, user.getHandle());
        Assert.assertEquals(newUserDisplayName, user.getDisplayName());
    }

    @Test
    public void addPost() {
        String newUserHandle = java.util.UUID.randomUUID().toString();
        String postText = "My new awesome post for user " + newUserHandle;
        User user = new User(newUserHandle);

        user.addPost(postText);

        Assert.assertEquals(1, user.getPosts().size());
        Assert.assertEquals(postText, user.getPosts().get(0).getContent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addPost_NullContent() {
        String newUserHandle = java.util.UUID.randomUUID().toString();
        User user = new User(newUserHandle);

        user.addPost(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addPost_EmptyContent() {
        String newUserHandle = java.util.UUID.randomUUID().toString();
        User user = new User(newUserHandle);

        user.addPost("");
    }

    @Test
    public void removePost() {
        String newUserHandle = java.util.UUID.randomUUID().toString();
        String postText = "My new awesome post for user " + newUserHandle;
        User user = new User(newUserHandle);
        user.addPost(postText);

        boolean removalResult = user.removePost(user.getPosts().get(0).getUniqueID());

        Assert.assertTrue(removalResult);
        Assert.assertEquals(0, user.getPosts().size());
    }

    @Test
    public void removePost_invalidId() {
        String newUserHandle = java.util.UUID.randomUUID().toString();
        String postText = "My new awesome post for user " + newUserHandle;
        User user = new User(newUserHandle);
        user.addPost(postText);

        boolean removalResult = user.removePost("notAvalidId");

        Assert.assertFalse(removalResult);
        Assert.assertEquals(1, user.getPosts().size());
    }

    @Test
    public void follow() {
        User mainUser = new User("main");
        User toFollowUser = new User("toFollow");

        mainUser.follow(toFollowUser);

        Assert.assertEquals(1, mainUser.getFollowings().size());
        Assert.assertTrue(mainUser.getFollowings().contains(toFollowUser));
        Assert.assertEquals(1, toFollowUser.getFollowers().size());
        Assert.assertTrue(toFollowUser.getFollowers().contains(mainUser));
    }

    @Test(expected = IllegalArgumentException.class)
    public void follow_nullUser() {
        User mainUser = new User("main");

        mainUser.follow(null);
    }

    @Test
    public void unfollow() {
        User mainUser = new User("main");
        User toUnfollowUser = new User("toFollow");
        mainUser.follow(toUnfollowUser);

        mainUser.unfollow(toUnfollowUser);

        Assert.assertEquals(0, mainUser.getFollowings().size());
        Assert.assertFalse(mainUser.getFollowings().contains(toUnfollowUser));
        Assert.assertEquals(0, toUnfollowUser.getFollowers().size());
        Assert.assertFalse(toUnfollowUser.getFollowers().contains(mainUser));
    }

    @Test
    public void getTimeLine() {
        final boolean useReverseOrder = false;
        User mainUser = new User("MyUser");

        Post firstPost = mainUser.addPost("1");
        Post secondPost = mainUser.addPost("2");
        Post lastPost = mainUser.addPost("3");

        Assert.assertEquals(3, mainUser.getWall(useReverseOrder).size());
        Assert.assertEquals(firstPost, mainUser.getWall(useReverseOrder).get(0));
        Assert.assertEquals(secondPost, mainUser.getWall(useReverseOrder).get(1));
        Assert.assertEquals(lastPost, mainUser.getWall(useReverseOrder).get(2));

    }

    @Test
    public void getTimeLine_reverseOrder() {
        final boolean useReverseOrder = true;
        User mainUser = new User("MyUser");

        Post firstPost = mainUser.addPost("1");
        Post secondPost = mainUser.addPost("2");
        Post lastPost = mainUser.addPost("3");

        Assert.assertEquals(3, mainUser.getWall(useReverseOrder).size());
        Assert.assertEquals(lastPost, mainUser.getWall(useReverseOrder).get(0));
        Assert.assertEquals(secondPost, mainUser.getWall(useReverseOrder).get(1));
        Assert.assertEquals(firstPost, mainUser.getWall(useReverseOrder).get(2));

    }

    @Test
    public void getWall() throws InterruptedException {
        final boolean useReverseOrder = false;
        User mainUser = new User("MyUser");
        User fwUserOne = new User("MyFollowingOne");
        User fwUserTwo = new User("MyFollowingTwo");
        User fwUserThree = new User("MyFollowingThree");
        mainUser.follow(fwUserOne);
        mainUser.follow(fwUserTwo);
        mainUser.follow(fwUserThree);

        Post firstPost = postWithDelay(fwUserThree, "3 - 1");
        postWithDelay(fwUserTwo, "2 - 1");
        postWithDelay(fwUserThree, "3 - 2");
        postWithDelay(fwUserOne, "1 - 1");
        postWithDelay(fwUserThree, "3 - 3");
        postWithDelay(fwUserTwo, "2 - 2");
        postWithDelay(fwUserOne, "1 - 2");
        Post lastPost = postWithDelay(fwUserOne, "1 - 3");


        Assert.assertEquals(3, fwUserOne.getWall(useReverseOrder).size());
        Assert.assertEquals(2, fwUserTwo.getWall(useReverseOrder).size());
        Assert.assertEquals(3, fwUserThree.getWall(useReverseOrder).size());
        Assert.assertEquals(0, mainUser.getWall(useReverseOrder).size());
        Assert.assertEquals(8, mainUser.getTimeLine(useReverseOrder).size());
        Assert.assertEquals(firstPost, mainUser.getTimeLine(useReverseOrder).get(0));
        Assert.assertEquals(lastPost, mainUser.getTimeLine(useReverseOrder).get(7));
    }

    @Test
    public void getWall_reverseOrder() throws InterruptedException {
        final boolean useReverseOrder = true;
        User mainUser = new User("MyUser");
        User fwUserOne = new User("MyFollowingOne");
        User fwUserTwo = new User("MyFollowingTwo");
        User fwUserThree = new User("MyFollowingThree");
        mainUser.follow(fwUserOne);
        mainUser.follow(fwUserTwo);
        mainUser.follow(fwUserThree);

        Post firstPost = postWithDelay(fwUserThree, "3 - 1");
        postWithDelay(fwUserTwo, "2 - 1");
        postWithDelay(fwUserThree, "3 - 2");
        postWithDelay(fwUserOne, "1 - 1");
        postWithDelay(fwUserThree, "3 - 3");
        postWithDelay(fwUserTwo, "2 - 2");
        postWithDelay(fwUserOne, "1 - 2");
        Post lastPost = postWithDelay(fwUserOne, "1 - 3");


        Assert.assertEquals(3, fwUserOne.getWall(useReverseOrder).size());
        Assert.assertEquals(2, fwUserTwo.getWall(useReverseOrder).size());
        Assert.assertEquals(3, fwUserThree.getWall(useReverseOrder).size());
        Assert.assertEquals(0, mainUser.getWall(useReverseOrder).size());
        Assert.assertEquals(8, mainUser.getTimeLine(useReverseOrder).size());
        Assert.assertEquals(lastPost, mainUser.getTimeLine(useReverseOrder).get(0));
        Assert.assertEquals(firstPost, mainUser.getTimeLine(useReverseOrder).get(7));
    }

    @Test
    public void getWall_NoFollows() {
        final boolean useReverseOrder = true;
        User mainUser = new User("MyUser");

        Assert.assertEquals(0, mainUser.getWall(useReverseOrder).size());
        Assert.assertEquals(0, mainUser.getTimeLine(useReverseOrder).size());
    }

    public void getPostById(){
        String newUserHandle = java.util.UUID.randomUUID().toString();
        String postText = "My new awesome post for user " + newUserHandle;
        User user = new User(newUserHandle);

        Post addedPost = user.addPost(postText);

        Assert.assertEquals(1, user.getPosts().size());
        Assert.assertEquals(user.getPostById(addedPost.getUniqueID()), addedPost);
    }

    @Test(expected = NoSuchElementException.class)
    public void getPostById_InvalidId(){
        String newUserHandle = java.util.UUID.randomUUID().toString();
        String postText = "My new awesome post for user " + newUserHandle;
        User user = new User(newUserHandle);

        user.addPost(postText);

        Assert.assertEquals(1, user.getPosts().size());
        user.getPostById("notAValidId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPostById_NullId(){
        String newUserHandle = java.util.UUID.randomUUID().toString();
        String postText = "My new awesome post for user " + newUserHandle;
        User user = new User(newUserHandle);

        user.addPost(postText);

        Assert.assertEquals(1, user.getPosts().size());
        user.getPostById(null);
    }

    private Post postWithDelay(User user, String postText) throws InterruptedException {
        Thread.sleep(1);
        return user.addPost(postText);
    }


}
