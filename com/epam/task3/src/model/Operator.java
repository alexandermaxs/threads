package com.epam.task3.model;

public class Operator {
    private String name;

    public Operator(String name){
        this.name = name;
    }

    public void serve(Client client){
        try {
            StringBuilder stringBuilder = new StringBuilder("");
            stringBuilder.append("Client#");
            stringBuilder.append(client.getNumber());
            stringBuilder.append(" is served by Operator#");
            stringBuilder.append(name);
            System.out.println(stringBuilder);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
