package com.babcock.user.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageChannels {

    String CREATE_USER_CHANNEL = "createUserChannel";
    String ACTIVATE_USER_CHANNEL = "activateUserChannel";

    @Input(CREATE_USER_CHANNEL)
    SubscribableChannel createUserChannel();

    @Input(ACTIVATE_USER_CHANNEL)
    SubscribableChannel activateUserChannel();
}
