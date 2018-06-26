package com.epam.task3.model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class CallCenter {
    private static volatile CallCenter instance;
    private BlockingQueue<Operator> staff = new ArrayBlockingQueue<>(3);
    private BlockingQueue<Client> clientele = new ArrayBlockingQueue<>(15);

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
        if (staff.peek() != null) {
            Operator active = staff.poll();
            active.serve(client);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            staff.add(active);
        }
        else {
            clientele.add(client);
        }
    }
}
