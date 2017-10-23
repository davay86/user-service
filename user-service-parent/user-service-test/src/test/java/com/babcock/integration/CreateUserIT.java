package com.babcock.integration;

import com.babcock.integration.asserter.WaitForHelper;
import com.babcock.integration.helper.DatabaseHelper;
import com.babcock.integration.helper.JsonConverter;
import com.babcock.integration.payload.User;
import com.babcock.integration.stream.MessageChannels;
import com.babcock.integration.application.TestApplication;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.*;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.List;

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

    @Autowired
    @Qualifier("oauthRestTemplate")
    private RestTemplate oAuthRestTemplate;

    @Autowired
    DatabaseHelper databaseHelper;

    @Autowired
    JsonConverter jsonConverter;

    @Value("${user.service.url}")
    String userServiceUrl;


    String uniqueStr;
    String userUsername;

    @Before
    public void before() throws InterruptedException {
        waitForHelper.waitForServices();

        uniqueStr = getUniqueString();

        userUsername = "username" + uniqueStr;

        databaseHelper.insertUser(userUsername,"firstname" + uniqueStr, "lastname" + uniqueStr);
        databaseHelper.insertActiveUser("activeUser" + uniqueStr,"activeFirstname" + uniqueStr, "activeLastname" + uniqueStr);
    }

    @After
    public void after(){
        databaseHelper.clearOutUsers();
    }

    @Test
    public void messageReceivedOn_createUserChannel_createCustomer_asExpected() throws InterruptedException {
        String payload = getExamplePayload(uniqueStr);

        logger.info("sending payload {}",payload);
        messageChannels.publishCreateUserChannel().send(createMessage(payload));

        String query = buildFindByUserNameQuery("joe"+uniqueStr);

        logger.info("waiting for db row from query {}",query);
        waitForHelper.waitForOneRowInDB(query);
    }

    @Test
    public void getPendingUsers() throws IOException {
        ResponseEntity<String> userList = oAuthRestTemplate.exchange(userServiceUrl + "/activateUser/getPending", HttpMethod.GET,null,String.class);

        List<User> users = jsonConverter.convertjsonStringToUserList(userList.getBody());

        Assert.assertEquals(1,users.size());

        User user= users.get(0);
        Assert.assertEquals("username" + uniqueStr, user.getUsername());
        Assert.assertEquals("firstname" + uniqueStr, user.getFirstname());
        Assert.assertEquals("lastname" + uniqueStr, user.getLastname());

    }

    @Test
    public void messageReceivedOn_activateUserChannel_activateCustomer_asExpected() throws InterruptedException {
        User userByUsername = databaseHelper.findUserByUsername(userUsername);


        String payload = getActivateUserPayload(userByUsername.getId());

        logger.info("sending payload {}",payload);
        messageChannels.publishActivateUserChannel().send(createMessage(payload));

        String query = buildFindActiveUserByUserNameQuery(userUsername);

        logger.info("waiting for db row from query {}",query);
        waitForHelper.waitForOneRowInDB(query);

    }


    public String buildFindByUserNameQuery(String username) {
        return "select count(*) from users where username = '"+username+"'";
    }

    public String buildFindActiveUserByUserNameQuery(String username) {
        return "select count(*) from users where username = '"+username+"' and active = true";
    }

    public String getExamplePayload(String uniqueStr) {
        return "{\"username\": \"joe"+uniqueStr+"\",\"firstname\": \"joe\",\"lastname\": \""+uniqueStr+"\"}";
    }

    public String getActivateUserPayload(long id){
        return "{\"id\": \""+id+"\"}";
    }

    private String getUniqueString() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    private Message<String> createMessage(String payload) {
        return new GenericMessage<>(payload);
    }
}
