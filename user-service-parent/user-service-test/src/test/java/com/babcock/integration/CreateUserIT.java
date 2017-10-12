package com.babcock.integration;

import com.babcock.integration.asserter.WaitForHelper;
import com.babcock.integration.asserter.WaitForOneRowInDB;
import com.babcock.integration.stream.MessageChannels;
import com.babcock.integration.application.TestApplication;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(CreateUserIT.class);

    @Autowired
    MessageChannels messageChannels;

    @Autowired
    private WaitForHelper waitForHelper;


    @Before
    public void before() throws InterruptedException {
        waitForHelper.waitForServices();
    }

    @Test
    public void consumeCreateUserChannel() throws InterruptedException {
        String uniqueStr = getUniqueString();
        String payload = getExamplePayload(uniqueStr);

        logger.info("sending payload {}",payload);
        messageChannels.publishCreateUserChannel().send(createMessage(payload));

        String query = buildFindByUserNameQuery("joe"+uniqueStr);

        logger.info("waiting for db row from query {}",query);
        waitForHelper.waitForOneRowInDB(query);
    }

    public String buildFindByUserNameQuery(String username) {
        return "select count(*) from users where username = '"+username+"'";
    }

    public String getExamplePayload(String uniqueStr) {
        return "{\"username\": \"joe"+uniqueStr+"\",\"firstname\": \"joe\",\"lastname\": \""+uniqueStr+"\"}";
    }

    private String getUniqueString() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    private Message<String> createMessage(String payload) {
        return new GenericMessage<>(payload);
    }

}
