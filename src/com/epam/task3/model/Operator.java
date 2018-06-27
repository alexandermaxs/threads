package com.epam.task3.model;

import org.apache.log4j.Logger;

public class Operator {
    private static final Logger logger = Logger.getLogger(CallCenter.class);
    private String name;

    public Operator(String name){
        this.name = name;
    }

    public void serve(Client client){
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("Client#");
        stringBuilder.append(client.getNumber());
        stringBuilder.append(" is served by Operator#");
        stringBuilder.append(name);
        logger.info(stringBuilder);
    }
}
