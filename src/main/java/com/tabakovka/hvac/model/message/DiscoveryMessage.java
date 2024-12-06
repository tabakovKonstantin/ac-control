package com.tabakovka.hvac.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class DiscoveryMessage {
    private String name;

    @JsonProperty("unique_id")
    private String uniqueId;

    @JsonProperty("power_command_topic")
    private String powerCommandTopic;

    @JsonProperty("temperature_command_topic")
    private String temperatureCommandTopic;

    @JsonProperty("fan_mode_command_topic")
    private String fanModeCommandTopic;

    @JsonProperty("mode_command_topic")
    private String modeCommandTopic;

    @JsonProperty("min_temp")
    private double minTemp;

    @JsonProperty("max_temp")
    private double maxTemp;

}
