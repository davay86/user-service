package com.babcock.user.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageChannels {

    String CREATE_USER_CHANNEL = "createUserChanel";

    @Input(CREATE_USER_CHANNEL)
    SubscribableChannel createUserChannel();
}
