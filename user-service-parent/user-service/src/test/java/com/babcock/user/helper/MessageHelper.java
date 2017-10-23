package com.babcock.user.helper;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
public class MessageHelper {

    public Message<String> createMessage(String payload) {
        return new GenericMessage<>(payload);
    }
}
