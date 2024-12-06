package com.tabakovka.hvac.model.message;

import com.tabakovka.hvac.ApplicationProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class MedeaDiscoveryMessage extends DiscoveryMessage {

    public MedeaDiscoveryMessage() {
        super();
    }

    public MedeaDiscoveryMessage createWithDefaults(ApplicationProperties properties) {
        var commands = properties.getIrRemoteControl().getCommands();
        return MedeaDiscoveryMessage.builder()
                .name(properties.getDevice().getName())
                .uniqueId(properties.getDevice().getId())
                .powerCommandTopic(commands.get("power").getInputTopic())
                .modeCommandTopic(commands.get("mode").getInputTopic())
                .temperatureCommandTopic(commands.get("temperature").getInputTopic())
                .fanModeCommandTopic(commands.get("fan").getInputTopic())
                .minTemp(20)
                .maxTemp(30)
                .build();
    }
}
