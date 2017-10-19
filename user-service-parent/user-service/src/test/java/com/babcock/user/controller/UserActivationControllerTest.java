package com.babcock.user.controller;

import com.babcock.user.application.TestApplication;
import com.babcock.user.model.domain.User;
import com.babcock.user.model.repositories.UserRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserActivationControllerTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @MockBean
    UserRepository userRepository;


    @Test
    public void getPending() throws Exception {

        String expectedMessage = "[{\"id\":0," +
                "\"username\":\"username\"," +
                "\"firstname\":\"firstaname\"" +
                ",\"lastname\":\"lastname\"," +
                "\"active\":false}]";

        String plainCreds = "admin:password";
        byte[] plainCredBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + base64Creds);

        User user = new User("username","firstaname","lastname");
        when(userRepository.findByActiveIsFalse()).thenReturn(Arrays.asList(user));

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> userList = testRestTemplate.exchange(getHost() + "/activateUser/getPending", HttpMethod.GET, entity, String.class);

        Assert.assertEquals(expectedMessage,userList.getBody());

    }

    private String getHost() {
        return "http://localhost:" + port + "/user-service";
    }

}