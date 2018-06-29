package com.epam.task3.model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
public class CallCenter {
    private static volatile CallCenter instance;
    private BlockingQueue<Operator> staff;

    public static CallCenter getInstance() {
        CallCenter localInstance = instance;
        if (localInstance == null) {
            synchronized (CallCenter.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CallCenter();
                }
            }
        }
        return localInstance;
    }

    public void initialize() {
        staff = new ArrayBlockingQueue<>(3);
        staff.add(new Operator("Olga"));
        staff.add(new Operator("Jean"));
        staff.add(new Operator("Diana"));
    }

    public Operator callOperator() {
        Operator active = staff.poll();
        staff.add(active);
        return active;
    }
}
