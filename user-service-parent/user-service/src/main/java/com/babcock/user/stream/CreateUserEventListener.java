package com.babcock.user.stream;

import com.babcock.user.model.domain.User;
import com.babcock.user.model.repositories.UserRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(MessageChannels.class)
public class CreateUserEventListener {

    private static Logger logger = LoggerFactory.getLogger(CreateUserEventListener.class);

    @Autowired
    UserRepository userRepository;

    @HystrixCommand
    @StreamListener(MessageChannels.CREATE_USER_CHANNEL)
    public void consumeCreateUserChannel(User user) {
        logger.info("UserPayload Received : "+ user);

        userRepository.save(user);
    }
}
