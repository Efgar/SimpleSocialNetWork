package com.efgh.simplenetwork.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Network {

    @Setter(AccessLevel.PRIVATE)
    @Getter
    public static ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    /**
     * Creates a new user in the system (without display name).
     *
     * @param newUserHandle handle for the user to create
     * @return the created user
     *
     * @throws IllegalArgumentException if the handle is empty or null
     */
    public static User addUser(String newUserHandle) {
        return addUser(newUserHandle, null);
    }

    /**
     * Creates a new user in the system.
     *
     * @param newUserHandle handle for the user to create
     * @param displayName display name for the user to create
     * @return the created user
     *
     * @throws IllegalArgumentException if the handle is empty or null
     */
    public static User addUser(String newUserHandle, String displayName) {
        User newUser = new User(newUserHandle, displayName);
        addUser(newUser);
        return newUser;
    }

    /**
     * Creates a new user in the system.
     *
     * @param user user to be added to the system
     * @return the created user
     * @throws IllegalArgumentException if there is any inconsistency with the user object definition
     */
    public static User addUser(User user) {
        user.validate();
        users.put(user.hashCode(), user);
        return user;
    }

    /**
     * Retrieves a user from the system with the specified handle.
     *
     * @param userHandle handle of the user to be retrieved
     * @return A user object containing the user related with the specified handle
     *
     * @throws IllegalArgumentException if the handle is empty or null
     * @throws NoSuchElementException if the provided handle does not correspond to any user on the system
     */
    public static User getUser(String userHandle) {
        User retrievedUser = users.get(new User(userHandle).hashCode());
        if(retrievedUser == null){
            throw new NoSuchElementException("User not found for the given handle");
        }
        return retrievedUser;
    }
}
