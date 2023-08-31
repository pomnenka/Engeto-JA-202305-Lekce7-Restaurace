package com.engeto.jt.restaurace;

public class Dish {
    int dishID;
    String dishName;
    int  dishPrice;
    int dishPrepTime;
    String dishCategory;

    public Dish(int dishID, String dishName, int dishPrice, int dishPrepTime, String dishCategory) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.dishPrepTime = dishPrepTime;
        this.dishCategory = dishCategory;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishID=" + dishID +
                ", dishName=" + dishName +
                ", dishPrice=" + dishPrice +
                ", dishPrepTime=" + dishPrepTime +
                ", dishCategory=" + dishCategory +
                '}';
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(int dishPrice) {
        this.dishPrice = dishPrice;
    }

    public int getDishPrepTime() {
        return dishPrepTime;
    }

    public void setDishPrepTime(int dishPrepTime) {
        this.dishPrepTime = dishPrepTime;
    }

    public String getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(String dishCategory) {
        this.dishCategory = dishCategory;
    }
}
