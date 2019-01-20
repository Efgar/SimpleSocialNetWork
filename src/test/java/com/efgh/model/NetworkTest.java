package com.efgh.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;

public class NetworkTest {

    @Test
    public void addUser() {
        String newUserHandle = java.util.UUID.randomUUID().toString();
        Network.addUser(newUserHandle);

        Assert.assertEquals(1, Network.getUsers().size());
        Assert.assertEquals(newUserHandle, Network.getUsers().elements().nextElement().getHandle());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addUser_invalidHandle() {
        Network.addUser(null);
    }

    @Test
    public void getUser() {
        String newUserHandle = java.util.UUID.randomUUID().toString();
        String newUserDisplay = "Display " + java.util.UUID.randomUUID().toString();
        Network.addUser(newUserHandle, newUserDisplay);

        Assert.assertNotNull(Network.getUser(newUserHandle));
        Assert.assertEquals(newUserDisplay, Network.getUser(newUserHandle).getDisplayName());
    }

    @Test(expected = NoSuchElementException.class)
    public void getUser_nonExistent() {
        String newUserHandle = java.util.UUID.randomUUID().toString();
        Assert.assertNotNull(Network.getUser(newUserHandle));
    }
}
