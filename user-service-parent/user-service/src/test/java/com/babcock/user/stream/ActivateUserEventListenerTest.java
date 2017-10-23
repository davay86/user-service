package com.babcock.user.stream;

import com.babcock.user.application.TestApplication;
import com.babcock.user.helper.MessageHelper;
import com.babcock.user.helper.PersistenceHelper;
import com.babcock.user.model.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
public class ActivateUserEventListenerTest {

    @Autowired
    MessageChannels messageChannels;

    @Autowired
    PersistenceHelper persistenceHelper;

    @Autowired
    MessageHelper messageHelper;

    User user;
    String username;
    String firstname;
    String lastname;

    @Before
    public void setup(){
        username = "username";
        firstname = "firstname";
        lastname = "lastname";

        user = persistenceHelper.createUser(username,firstname,lastname);
    }

    @Test
    public void consumeActivateUserChannel() throws Exception {
        String payload = "{ \"id\" : \"" + user.getId() + "\"}";

        messageChannels.activateUserChannel().send(messageHelper.createMessage(payload));

        User foundUser = persistenceHelper.findByUsername(username);

        Assert.assertEquals(username, foundUser.getUsername());
        Assert.assertEquals(firstname, foundUser.getFirstname());
        Assert.assertEquals(lastname, foundUser.getLastname());
        Assert.assertEquals(true,foundUser.isActive());
    }

}