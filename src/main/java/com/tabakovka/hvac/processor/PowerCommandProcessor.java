package com.tabakovka.hvac.processor;


import com.tabakovka.hvac.ApplicationProperties;
import org.springframework.stereotype.Service;

@Service
public class PowerCommandProcessor extends AbstractIrCommandProcessor {

    private final static String COMMAND_NAME = "power";

    public PowerCommandProcessor(ApplicationProperties properties) {
        super(properties);
    }

    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }

}
