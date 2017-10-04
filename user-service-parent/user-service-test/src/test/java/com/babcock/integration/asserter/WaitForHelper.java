package com.babcock.integration.asserter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.fail;

@Component
@TestPropertySource("classpath:application.properties")
public class WaitForHelper {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.service.url}")
    String baseUrl;

    private static boolean serviceUnavailable = false;

    public void waitForServices() throws InterruptedException {
        WaitForService waitForService = new WaitForService(baseUrl + "/info", restTemplate);
        waitForService.setMaxWaitTime(720000);

        if(serviceUnavailable) {
            fail("user-service docker environment unavailable");
        }

        System.out.println("waiting for service : " + baseUrl + "/info");

        try {
            waitForService.performAssertion();
        }catch (WaitUntilAssertionError wae) {
            serviceUnavailable = true;
            fail("user-service docker environment unavailable");
        }
    }

}
