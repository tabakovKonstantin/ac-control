package com.tabakovka.hvac.processor;


import com.tabakovka.hvac.model.IrCommand;

public interface IrCommandProcessor {
    IrCommand processCommandValue(String request);
    String getTopic();
}
