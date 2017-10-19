package com.babcock.user.model.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private User user;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testDefaultConstructor(){
        user = new User();
        Assert.assertNull(user.getFirstname());
        Assert.assertNull(user.getLastname());
        Assert.assertNull(user.getUsername());

    }

    @Test
    public void testConstructor(){
        user = new User("testUsername", "testFirstname", "testLastname");

        Assert.assertEquals("testUsername", user.getUsername());
        Assert.assertEquals("testFirstname", user.getFirstname());
        Assert.assertEquals("testLastname", user.getLastname());

    }

    @Test
    public void getId() throws Exception {
        user = new User();
        user.setId(123L);

        Assert.assertEquals(123L,user.getId().longValue());
    }

    @Test
    public void getUsername() throws Exception {
        user = new User();
        user.setUsername("testUsername");

        Assert.assertEquals("testUsername",user.getUsername());
    }

    @Test
    public void getFirstname() throws Exception {
        user = new User();
        user.setFirstname("testFirstname");

        Assert.assertEquals("testFirstname", user.getFirstname());
    }

    @Test
    public void getLastname() throws Exception {
        user = new User();
        user.setLastname("testLastname");

        Assert.assertEquals("testLastname", user.getLastname());
    }

    @Test
    public void getActive() throws Exception {
        user= new User();
        user.setActive(true);

        Assert.assertTrue(user.isActive());
    }

}