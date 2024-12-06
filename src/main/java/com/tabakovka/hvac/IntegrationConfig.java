package com.tabakovka.hvac;

import com.tabakovka.hvac.processor.IrCommandProcessor;
import lombok.AllArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.List;

@AllArgsConstructor
@Configuration
public class IntegrationConfig {

    private final ApplicationProperties properties;
    private final List<IrCommandProcessor> irCommandProcessors;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        var mqttBrokerUrl = properties.getMqtt().getUrl();
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{mqttBrokerUrl});
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageProducerSupport mqttInbound(MqttPahoClientFactory clientFactory) {
        var clientId = properties.getMqtt().getSubClientId();
        var topics = irCommandProcessors.stream().map(IrCommandProcessor::getTopic).toList();

        var adapter = new MqttPahoMessageDrivenChannelAdapter(clientId, clientFactory, topics.toArray(new String[0]));
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        return adapter;
    }

    @Bean
    public IntegrationFlow mqttInFlow(MessageProducerSupport mqttInbound, MessageChannel outputChannel) {
        return IntegrationFlow.from(mqttInbound)
                .log("fromHA")
                .route("headers['mqtt_receivedTopic']",
                        mapping -> irCommandProcessors.forEach(processor ->
                        mapping.subFlowMapping(processor.getTopic(),sf ->
                                sf.transform(processor::processCommandValue))))
                .channel(outputChannel)
                .get();
    }

    @Bean
    public MessageChannel outputChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow mqttOutFlow(MessageChannel outputChannel) {
        return IntegrationFlow.from(outputChannel)
                .log("toMqtt")
                .transform(new ObjectToJsonTransformer())
                .handle(mqttOutbound())
                .get();
    }

    @Bean
    public MessageHandler mqttOutbound() {
        var clientId = properties.getMqtt().getPubClientId();
        var topic = properties.getIrRemoteControl().getOutputTopic();

        var messageHandler = new MqttPahoMessageHandler(clientId, mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(topic);
        return messageHandler;
    }
}
