package com.tabakovka.hvac.processor;

import com.tabakovka.hvac.ApplicationProperties;
import com.tabakovka.hvac.model.IrCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractIrCommandProcessor implements IrCommandProcessor {

    protected final ApplicationProperties properties;

    @Override
    public IrCommand processCommandValue(String commandValue) {
        var irCode = getCodeByCommandForCurrDevice(commandValue);
        return new IrCommand(irCode);
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
