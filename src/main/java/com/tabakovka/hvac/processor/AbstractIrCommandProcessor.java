package com.tabakovka.hvac.processor;

import com.tabakovka.hvac.ApplicationProperties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractIrCommandProcessor implements IrCommandProcessor {

    protected final ApplicationProperties properties;

    @Override
    public String processCommandValue(String commandValue) {
       return getCodeByCommandForCurrDevice(commandValue);
    }

    @Override
    public String getTopic() {
        return properties.getIrRemoteControl().getCommands().get(getCommandName()).getInputTopic();
    }

    protected String getCodeByCommandForCurrDevice(String commandValue) {
        var deviceName = properties.getDevice().getName();
        return properties
                .getIrRemoteControl()
                .getCommands()
                .get(getCommandName())
                .getVendors()
                .get(deviceName)
                .get(commandValue);
    }

    protected abstract String getCommandName();
}
