package com.engeto.jt.restaurace;

public class Picture {
    private int dishID;
    private String picturePath;

    public Picture(int dishID, String picturePath) {
        this.dishID = dishID;
        this.picturePath = picturePath;
    }

    public int getDishID() {
        return dishID;
    }

    public void setPictureID(int dishID) {
        this.dishID = dishID;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public String toString() {
        return "     Picture{" +
                "dishID=" + dishID +
                ", picturePath='" + picturePath + '\'' +
                '}';
    }
}
