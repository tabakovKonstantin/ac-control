package com.tabakovka.hvac.discovery;

import com.tabakovka.hvac.ApplicationProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    private final ApplicationProperties properties;
    private final DiscoveryMessagePublisher publisher;
    private final DiscoveryMessageFactory factory;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        sendDiscoveryMessage();
    }

    private void sendDiscoveryMessage() {
        var payload = factory.createMessage(properties.getDevice().getName());
        var message = MessageBuilder.withPayload(payload)
                .setHeader("mqtt_topic", properties.getDiscoveryTopic())
                .build();
        publisher.publish(message);
    }

}