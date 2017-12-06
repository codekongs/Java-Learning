package com.callingsystem;

public class Waiter {
    private final int id = counter++;
    private static int counter = 1;

    public int getId(){
        return id;
    }

    public String toString() {
        if (id > 9)
            return "Waiter [id=" + id + "]";
        return "Waiter [id=0" + id + "]";

    }
}