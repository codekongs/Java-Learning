package com.callingsystem;

public class Customer {
    private final int id = counter++;
    private static int counter = 1;

    public int getId(){
        return id;
    }

    public String toString() {
        if (id > 9) {
            return "Customer [id=" + id + "]";
        }
        return "Customer [id=0" + id + "]";
    }
}