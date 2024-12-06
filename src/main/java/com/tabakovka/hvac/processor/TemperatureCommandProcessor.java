package com.tabakovka.hvac.processor;


import com.tabakovka.hvac.ApplicationProperties;
import org.springframework.stereotype.Service;

@Service
public class TemperatureCommandProcessor extends AbstractIrCommandProcessor {

    private final static String COMMAND_NAME = "temperature";

    public TemperatureCommandProcessor(ApplicationProperties properties) {
        super(properties);
    }

    @Override
    protected String getCodeByCommandForCurrDevice(String commandValue) {
        int roundTemp = Double.valueOf(commandValue).intValue();
        return super.getCodeByCommandForCurrDevice(String.valueOf(roundTemp));
    }

    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }

}
