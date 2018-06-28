package com.epam.task3.model;

import org.apache.log4j.Logger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Operator {
    private static final Logger LOGGER = Logger.getLogger(Operator.class);
    private String name;
    private ReentrantLock locker = new ReentrantLock();

    public Operator(String name){
        this.name = name;
    }

    public void serve(Client client){
        locker.lock();
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("Client#");
        stringBuilder.append(client.getNumber());
        stringBuilder.append(" is served by Operator#");
        stringBuilder.append(name);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
        LOGGER.info(stringBuilder);
        locker.unlock();
    }
}
