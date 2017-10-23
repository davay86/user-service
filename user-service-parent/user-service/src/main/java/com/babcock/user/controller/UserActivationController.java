package com.babcock.user.controller;

import com.babcock.user.model.domain.User;
import com.babcock.user.model.repositories.UserRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/activateUser")
public class UserActivationController {

    @Autowired
    UserRepository userRepository;

    @HystrixCommand
    @RequestMapping(value = "/getPending", method = RequestMethod.GET)
    public List<User> getPending() {
        return userRepository.findByActiveIsFalse();
    }
}
