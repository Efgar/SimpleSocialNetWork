package com.efgh.simplenetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class User implements Comparable<User> {

    @Setter(AccessLevel.PRIVATE)

    private String handle;

    @EqualsAndHashCode.Exclude
    private String displayName;

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private ArrayList<Post> posts = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private HashSet<User> followings = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private HashSet<User> followers = new HashSet<>();

    /**
     * Creates an instance of a user receiving only the identifier,
     * have in mind that this will create a user with no display name
     *
     * @param handle The user unique identifier.
     * @throws IllegalArgumentException if the handle is empty or null
     */
    User(String handle) {
        this(handle, null);
    }

    /**
     * Creates an instance of a user receiving both the identifier
     * and the display name
     *
     * @param handle      The user unique identifier.
     * @param displayName The user name to be shown.
     * @throws IllegalArgumentException if the handle is empty or null
     */
    User(String handle, String displayName) {
        Assert.isTrue(StringUtils.isNotBlank(handle), "Handle can not be empty");
        setHandle(handle);
        if (StringUtils.isNotBlank(displayName)) {
            setDisplayName(displayName);
        }
    }

    /**
     * Add a post on the calling user account
     *
     * @param postContent Text content to be published, see {@link Post} constraints.
     * @throws IllegalArgumentException if the content is null, empty, or it´s length is above 140 characters
     */
    public Post addPost(String postContent) {
        Post postToAdd = new Post(postContent);
        getPosts().add(postToAdd);
        return postToAdd;
    }

    /**
     * Remove post given the specified Id
     *
     * @param postId Unique identifier od the post
     * @return true if any value has been deleted, false otherwise
     */
    public boolean removePost(String postId) {
        return getPosts().removeIf(p -> p.getUniqueID().equalsIgnoreCase(postId));
    }

    private void addFollower(User follower) {
        Assert.notNull(follower, "Follower user can not be null");
        getFollowers().add(follower);
    }

    /**
     * Add a user to the list of currently following users, please note that this also add the calling
     * user to the "followers list" of the user to be followed.
     *
     * @param toFollowUser User to be followed
     * @throws IllegalArgumentException if the received user is null
     */
    public void follow(User toFollowUser) {
        Assert.notNull(toFollowUser, "User to follow can not be null");
        getFollowings().add(toFollowUser);
        toFollowUser.addFollower(this);
    }

    /**
     * Remove user of the "Following list", note that this also remove the calling user from the received
     * user followers list
     *
     * @param toUnfollowUser user to be unfollow
     * @throws IllegalArgumentException if the received user is null
     */
    public void unfollow(User toUnfollowUser) {
        Assert.notNull(toUnfollowUser, "User to un-follow can not be null");
        getFollowings().remove(toUnfollowUser);
        toUnfollowUser.removeFollower(this);
    }

    private void removeFollower(User follower) {
        Assert.notNull(follower, "Follower user can not be null");
        getFollowers().remove(follower);
    }

    /**
     * Get the compilation of all the posts made by the user.
     * @param reverseOrder false to retrieve the posts in chronological order, true to retrieve
     *                     them in inverse chronological order
     * @return List containing the posts, sorted according what is specified
     */
    public ArrayList<Post> getWall(boolean reverseOrder) {
        Comparator<Post> comparatorToUse = reverseOrder ? Comparator.reverseOrder() : Comparator.naturalOrder();
        getPosts().sort(comparatorToUse);
        return getPosts();
    }

    /**
     * Get the compilation of all the posts made by the users being followed.
     * @param reverseOrder false to retrieve the posts in chronological order, true to retrieve
     *                     them in inverse chronological order
     * @return List containing the posts, sorted according what is specified
     */
    public List<Post> getTimeLine(boolean reverseOrder) {
        Comparator<Post> comparatorToUse = reverseOrder ? Comparator.reverseOrder() : Comparator.naturalOrder();
        return followings.stream().flatMap(user -> user.getPosts().stream()).sorted(comparatorToUse).collect(Collectors.toList());
    }

    /**
     * Retrieves a post given its unique ID
     *
     * @param uniqueId Id of the post to be retrieved
     * @return The post whit id equals to the one specified
     * @throws NoSuchElementException if there is no post for the given ID
     * @throws IllegalArgumentException if the received Id is empty
     *
     */
    public Post getPostById(String uniqueId) {
        Assert.isTrue(StringUtils.isNotBlank(uniqueId), "Invalid argument for the post search");
        return getPosts().stream().filter( post -> post.getUniqueID().equalsIgnoreCase(uniqueId)).findFirst().orElseThrow(() -> new NoSuchElementException("No element found for the given id"));
    }

    /**
     * Copy the values of the received object into the current one without changing the current user handle.
     *
     * @param user object whose values are to be copy
     */
    public void copy(User user) {
        if (user == null) {
            return;
        }
        setDisplayName(user.getDisplayName());
    }

    /**
     * Validate if the current user is properly configured
     *
     * @throws NoSuchElementException if there is any inconsistency with the current object definition
     */
    public void validate() {
        Assert.isTrue(StringUtils.isNotBlank(getHandle()), "Invalid argument for the user handle");
    }

    @Override
    public int compareTo(User user) {
        return getHandle().compareTo(user.getHandle());
    }
}
