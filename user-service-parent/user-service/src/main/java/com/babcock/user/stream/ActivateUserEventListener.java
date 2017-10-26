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
public class ActivateUserEventListener {

    private static Logger logger = LoggerFactory.getLogger(ActivateUserEventListener.class);

    @Autowired
    UserRepository userRepository;

    @HystrixCommand
    @StreamListener(MessageChannels.ACTIVATE_USER_CHANNEL)
    public void consumeActivateUserChannel(User user) {
        logger.info("UserPayload Received : "+ user);

        User foundUser = userRepository.findOne(user.getId());
        foundUser.setActive(true);

        userRepository.save(foundUser);
    }
}
