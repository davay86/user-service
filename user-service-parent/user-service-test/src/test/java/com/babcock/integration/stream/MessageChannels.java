package com.babcock.integration.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface MessageChannels {

    String PUBLISH_CREATE_USER_CHANNEL = "publishCreateUserChannel";

    @Output(PUBLISH_CREATE_USER_CHANNEL)
    SubscribableChannel publishCreateUserChannel();
}
