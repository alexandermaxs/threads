package com.epam.task3;

import com.epam.task3.model.CallCenter;
import com.epam.task3.model.Client;
import org.apache.log4j.LogManager;
import org.apache.log4j.xml.DOMConfigurator;

public class Starter {
    public static void main(String[] args){
        new DOMConfigurator().doConfigure("log4j.xml", LogManager.getLoggerRepository());
        CallCenter.getInstance().initialize();
        generateCalls(15);
    }

    public static void generateCalls(int count) {
        for (int i = 0; i < count; i++) {
            int number = i + 1;
            new Thread(new Client(number)).start();
        }
    }
}
