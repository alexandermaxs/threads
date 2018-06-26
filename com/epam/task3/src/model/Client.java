package com.epam.task3.model;

public class Client implements Runnable{
    private int number;

    public Client(int number){
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public void run() {
        CallCenter.getInstance().connect(this);
    }
}
