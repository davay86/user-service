package com.babcock.integration;

import com.babcock.integration.asserter.WaitForHelper;
import com.babcock.integration.stream.MessageChannels;
import com.babcock.integration.application.TestApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= TestApplication.class)
@EnableBinding(MessageChannels.class)
@TestPropertySource("classpath:application.properties")
public class CreateUserIT {

    @Autowired
    MessageChannels messageChannels;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private WaitForHelper waitForHelper;


    @Before
    public void before() throws InterruptedException {
        waitForHelper.waitForServices();
    }

    @Test
    public void consumeCreateUserChannel(){
        String payload = "{ \"username\" : \"testUser\"," +
                "\"firstname\" : \"Test\"," +
                "\"lastname\" : \"User\"" +
                "}";

        messageChannels.publishCreateUserChannel().send(createMessage(payload));

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from users");

        Assert.assertEquals(1, list.size());
    }

    private Message<String> createMessage(String payload) {
        return new GenericMessage<>(payload);
    }

}
