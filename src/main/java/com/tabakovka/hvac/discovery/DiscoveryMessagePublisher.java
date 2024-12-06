package com.tabakovka.hvac.discovery;

import com.tabakovka.hvac.model.message.DiscoveryMessage;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DiscoveryMessagePublisher {

    private final MessageChannel outputChannel;

    public void publish(Message<DiscoveryMessage> message) {
        outputChannel.send(message);
    }
}
