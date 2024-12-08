package com.tabakovka.hvac.discovery;

import com.tabakovka.hvac.ApplicationProperties;
import com.tabakovka.hvac.model.message.DiscoveryMessage;
import com.tabakovka.hvac.model.message.MedeaDiscoveryMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DiscoveryMessageFactory {
    private final ApplicationProperties properties;

    public DiscoveryMessage createMessage(String deviceName) {
        return switch (deviceName) {
            case "Midea" -> new MedeaDiscoveryMessage().createWithDefaults(properties);
            default -> throw new IllegalArgumentException("Unsupported device: " + deviceName);
        };
    }
}
