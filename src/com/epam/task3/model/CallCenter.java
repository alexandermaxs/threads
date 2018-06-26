package com.epam.task3.model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class CallCenter {
    private static volatile CallCenter instance;
    private BlockingQueue<Operator> staff = new ArrayBlockingQueue<>(3);
    //private BlockingQueue<Client> clientele = new ArrayBlockingQueue<>(15);
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

    public void setStaff(BlockingQueue<Operator> staff) {
        this.staff = staff;
    }

    public void connect(Client client){
        locker.lock();
        try {
            while (staff.isEmpty()) {
                condition.await();
            }
            Operator active = staff.poll();
            active.serve(client);
            TimeUnit.SECONDS.sleep(2);
            staff.add(active);
            //clientele.add(client);
            condition.signalAll();

        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            locker.unlock();
        }
    }
}
