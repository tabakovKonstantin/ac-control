package com.tabakovka.hvac;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private Device device;
    private String discoveryTopic;
    private IrRemoteControl irRemoteControl;
    private Mqtt mqtt;

    @Data
    public static class Device {
        private String id;
        private String name;
    }

    @Data
    public static class IrRemoteControl {
        private String outputTopic;
        private Map<String, Command> commands;
    }

    @Data
    public static class Command {
        private String inputTopic;
        private Map<String, Map<String, String>> vendors;
    }

    @Data
    public static class Mqtt {
        private String url;
        private String subClientId;
        private String pubClientId;
    }
}
