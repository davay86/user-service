package com.babcock.user.model.repositories;

import com.babcock.user.model.domain.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@SpringBootTest(classes = User.class)
@RunWith(SpringRunner.class)
@DataJpaTest
@EntityScan("com.babcock.user.model.domain")
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    protected TestEntityManager entityManager;

    long userId;
    long nonActivateUserId;

    User user;
    User nonActivatedUser;

    @Before
    public void setup(){
        user = new User("testUsername", "testFirstname", "testLastname");
        user.setActive(true);
        entityManager.persist(user);
        userId = user.getId();

        nonActivatedUser = new User("nonActivateUser", "nonActivatedFirstname", "nonActivatedLastname");
        entityManager.persist(nonActivatedUser);
        nonActivateUserId = nonActivatedUser.getId();
    }

    @After
    public void teardown(){
        entityManager.remove(user);
    }

    @Test
    public void findById(){
        User one = userRepository.findOne(userId);

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