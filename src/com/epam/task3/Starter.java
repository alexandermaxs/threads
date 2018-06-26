package com.epam.task3;

import com.epam.task3.model.CallCenter;
import com.epam.task3.model.Client;
import com.epam.task3.model.Operator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Starter {
    public static void main(String[] args){
        BlockingQueue<Operator> staff = new ArrayBlockingQueue<>(3);
        staff.add(new Operator("Olga"));
        staff.add(new Operator("Jean"));
        staff.add(new Operator("Diana"));
        CallCenter.getInstance().setStaff(staff);

        generateCalls(15);
    }

    public static void generateCalls(int count) {
        for (int i = 0; i < count; i++) {
            int number = i + 1;
            new Thread(new Client(number)).start();
        }
    }
}
