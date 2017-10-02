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

    User user;

    @Before
    public void setup(){
        user = new User("testUsername", "testFirstname", "testLastname");

        entityManager.persist(user);

        userId = user.getId();
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
}