package com.babcock.user.helper;


import com.babcock.user.model.domain.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.List;

@Component
@TestPropertySource("classpath:application.properties")
public class JsonConverter {

    public List<User> convertjsonStringToUserList(String json){
        ObjectMapper mapper = new ObjectMapper();
        List<User> list = null;
        try {
            list = mapper.readValue(json, new TypeReference<List<User>>(){});
        } catch (IOException e) {
            Assert.fail("Failed to convert json to list of users.");
        }
        return list;
    }
}
