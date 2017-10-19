package com.babcock.integration.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;

@Component
@TestPropertySource("classpath:application.properties")
public class DatabaseHelper {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public void insertUser(String username,String firstname, String lastname){
        jdbcTemplate.update("INSERT INTO users (USERNAME, FIRSTNAME, LASTNAME) VALUES(?,?,?)",
                username,firstname, lastname
        );
    }

    public void insertActiveUser(String username,String firstname, String lastname){
        jdbcTemplate.update("INSERT INTO users (USERNAME, FIRSTNAME, LASTNAME, ACTIVE) VALUES(?,?,?,?)",
                username,firstname, lastname, true
        );
    }

    public void clearOutUsers(){
        jdbcTemplate.update("DELETE FROM users");
    }

}
