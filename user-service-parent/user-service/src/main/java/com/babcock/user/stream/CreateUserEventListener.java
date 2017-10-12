package com.babcock.user.stream;

import com.babcock.user.model.domain.User;
import com.babcock.user.model.repositories.UserRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(MessageChannels.class)
public class CreateUserEventListener {

    @Autowired
    UserRepository userRepository;

    @HystrixCommand
    @StreamListener(MessageChannels.CREATE_USER_CHANNEL)
    public void consumeCreateUserChannel(User user) {
        userRepository.save(user);
    }
}
