package com.epam.task3.model;

import org.apache.log4j.Logger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class CallCenter {
    private static final Logger LOGGER = Logger.getLogger(CallCenter.class);
    private static volatile CallCenter instance;
    private BlockingQueue<Operator> staff;
    private ReentrantLock locker = new ReentrantLock();
    private Condition condition = locker.newCondition();

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
        locker.lock();
        while (staff.isEmpty()) {
            try {
                condition.await();
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
        }
        Operator active = staff.poll();
        staff.add(active);
        condition.signalAll();
        locker.unlock();
        return active;
    }
}
