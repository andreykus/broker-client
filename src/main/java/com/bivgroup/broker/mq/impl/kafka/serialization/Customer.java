package com.bivgroup.broker.mq.impl.kafka.serialization;

/**
 * Created by bush on 20.06.2016.
 */
public class Customer {
    private int customerID;
    private String customerName;

    public Customer(int ID, String name) {
        this.customerID = ID;
        this.customerName = name;
    }

    public int getID() {
        return customerID;
    }

    public String getName() {
        return customerName;
    }
}
