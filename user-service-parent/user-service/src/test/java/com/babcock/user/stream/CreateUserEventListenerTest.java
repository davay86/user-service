package com.babcock.user.stream;

import com.babcock.user.application.TestApplication;
import com.babcock.user.helper.MessageHelper;
import com.babcock.user.helper.PersistenceHelper;
import com.babcock.user.model.domain.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
public class CreateUserEventListenerTest {

    @Autowired
    MessageChannels messageChannels;

    @Autowired
    MessageHelper messageHelper;

    @Autowired
    PersistenceHelper persistenceHelper;

    User createdUser;

    @Test
    public void consumeCreateUserChannel() throws Exception {
        String payload = "{ \"username\" : \"testUser\"," +
                            "\"firstname\" : \"Test\"," +
                            "\"lastname\" : \"User\"" +
                "}";

        messageChannels.createUserChannel().send(messageHelper.createMessage(payload));

        createdUser = persistenceHelper.findByUsername("testUser");

        Assert.assertEquals("testUser", createdUser.getUsername());
        Assert.assertEquals("Test", createdUser.getFirstname());
        Assert.assertEquals("User", createdUser.getLastname());
    }

    @After
    public void teardown(){
        persistenceHelper.removeUser(createdUser);
    }

}