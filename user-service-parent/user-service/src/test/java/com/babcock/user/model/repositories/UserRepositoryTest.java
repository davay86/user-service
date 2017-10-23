package com.babcock.user.model.repositories;

import com.babcock.user.application.TestApplication;
import com.babcock.user.helper.PersistenceHelper;
import com.babcock.user.model.domain.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@EntityScan("com.babcock.user.model.domain")
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersistenceHelper persistenceHelper;

    User user;
    User nonActivatedUser;

    @Before
    public void setup(){
        user = persistenceHelper.createActiveUser("testUsername", "testFirstname", "testLastname");
        nonActivatedUser = persistenceHelper.createUser("nonActivateUser", "nonActivatedFirstname", "nonActivatedLastname");
    }

    @After
    public void teardown(){
        persistenceHelper.removeUser(user);
        persistenceHelper.removeUser(nonActivatedUser);
    }

    @Test
    public void findById(){
        User one = userRepository.findOne(user.getId());

        Assert.assertNotNull(one);
        Assert.assertEquals("testUsername", one.getUsername());
        Assert.assertEquals("testFirstname", one.getFirstname());
        Assert.assertEquals("testLastname", one.getLastname());
    }

    @Test
    public void findByActiveIsFalse(){
        List<User> nonActivatedUsers = userRepository.findByActiveIsFalse();

        Assert.assertEquals(1, nonActivatedUsers.size());
        User user = nonActivatedUsers.get(0);
        Assert.assertEquals("nonActivateUser", user.getUsername());
        Assert.assertEquals("nonActivatedFirstname", user.getFirstname());
        Assert.assertEquals("nonActivatedLastname", user.getLastname());
    }
}