package com.babcock.user.controller;

import com.babcock.user.application.TestApplication;
import com.babcock.user.helper.JsonConverter;
import com.babcock.user.helper.PersistenceHelper;
import com.babcock.user.model.domain.User;
import com.babcock.user.model.repositories.UserRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserActivationControllerTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Autowired
    PersistenceHelper persistenceHelper;

    @Autowired
    JsonConverter jsonConverter;



    User user;
    User activeUser;
    @Before
    public void setup(){
        user = persistenceHelper.createUser("username","firstname", "lastname" );
        activeUser = persistenceHelper.createActiveUser("activeUsername","activeFirstname", "activeLastname");
    }


    @Test
    public void getPending() throws Exception {

        String plainCreds = "admin:password";
        byte[] plainCredBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + base64Creds);

        User user = new User("username","firstname","lastname");

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> userList = testRestTemplate.exchange(getHost() + "/activateUser/getPending", HttpMethod.GET, entity, String.class);

        List<User> users = jsonConverter.convertjsonStringToUserList(userList.getBody());

        Assert.assertEquals(1,users.size());
        Assert.assertEquals("username",user.getUsername());
        Assert.assertEquals("firstname",user.getFirstname());
        Assert.assertEquals("lastname",user.getLastname());
        Assert.assertEquals(false,user.isActive());

    }

    private String getHost() {
        return "http://localhost:" + port + "/user-service";
    }

}