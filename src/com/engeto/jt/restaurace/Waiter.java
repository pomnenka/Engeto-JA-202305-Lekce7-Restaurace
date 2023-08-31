package com.engeto.jt.restaurace;

import java.util.ArrayList;
import java.util.List;

public class Waiter {
    private static int nextID = 1;
    private int waiterID;
    private String waiterName;
    private int numberOfOrders;
    private int amountOfMoney;

    private static List<Waiter> waiters = new ArrayList<>();

    public Waiter(int waiterID, String waiterName, int numberOfOrders, int amountOfMoney) {
        this.waiterID = waiterID;
        this.waiterName = waiterName;
        this.numberOfOrders = numberOfOrders;
        this.amountOfMoney = amountOfMoney;
        waiters.add(this);
        if(nextID<=waiterID) { nextID = waiterID + 1;}
    }

    public Waiter(String waiterName) {
        this(nextID++, waiterName, 0,0);
    }

    public int getWaiterID() {
        return waiterID;
    }

    public void setWaiterID(int waiterID) {
        this.waiterID = waiterID;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public static List<Waiter> getWaitres () {
        return new ArrayList<>(waiters);
    }
    @Override
    public String toString() {
        return "Waiter{" +
                "waiterID=" + waiterID +
                ", waiterName='" + waiterName + '\'' +
                ", numberOfOrders=" + numberOfOrders +
                ", amountOfMoney=" + amountOfMoney +
                '}';
    }
}
