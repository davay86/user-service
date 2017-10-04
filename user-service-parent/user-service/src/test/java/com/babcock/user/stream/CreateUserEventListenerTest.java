package com.babcock.user.stream;

import com.babcock.user.application.TestApplication;
import com.babcock.user.model.domain.User;
import com.babcock.user.model.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Payload;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
public class CreateUserEventListenerTest {

    @Autowired
    MessageChannels messageChannels;

    @Autowired
    UserRepository userRepository;

    @Test
    public void consumeCreateUserChannel() throws Exception {
        String payload = "{ \"username\" : \"testUser\"," +
                            "\"firstname\" : \"Test\"," +
                            "\"lastname\" : \"User\"" +
                "}";

        messageChannels.createUserChannel().send(createMessage(payload));

        User foundUser = userRepository.findAll().get(0);

        Assert.assertEquals("testUser", foundUser.getUsername());
        Assert.assertEquals("Test", foundUser.getFirstname());
        Assert.assertEquals("User", foundUser.getLastname());
    }

    private Message<String> createMessage(String payload) {
        return new GenericMessage<>(payload);
    }

}