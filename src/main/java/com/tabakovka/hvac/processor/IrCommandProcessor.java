package com.tabakovka.hvac.processor;


public interface IrCommandProcessor {
    String processCommandValue(String request);
    String getTopic();
}
