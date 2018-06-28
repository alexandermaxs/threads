package com.epam.task3.model;

import org.apache.log4j.Logger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class CallCenter {
    private static final Logger LOGGER = Logger.getLogger(CallCenter.class);
    private static volatile CallCenter instance;
    private BlockingQueue<Operator> staff = new ArrayBlockingQueue<>(3);
    ReentrantLock locker = new ReentrantLock();
    Condition condition = locker.newCondition();

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
        staff.add(new Operator("Olga"));
        staff.add(new Operator("Jean"));
        staff.add(new Operator("Diana"));
    }

    public void connect(Client client){
        try {
            while (staff.isEmpty()) {
                condition.await();
            }
            locker.lock();
            Operator active = staff.poll();
            active.serve(client);
            TimeUnit.SECONDS.sleep(2);
            staff.add(active);
            condition.signalAll();
        } catch (InterruptedException e){
            LOGGER.error(e);
        } finally {
            locker.unlock();
        }
    }
}
