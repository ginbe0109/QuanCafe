package com.example.user.quancafe.activity.model;

/**
 * Created by User on 19/07/2018.
 */

public class Hoadon {
    private int sohd;
    private int mand;
    private String thoigian;
    private float trigia;

    public Hoadon(int sohd, int mand, String thoigian, float trigia) {
        this.sohd = sohd;
        this.mand = mand;
        this.thoigian = thoigian;
        this.trigia = trigia;
    }

    public Hoadon() {
    }

    public int getSohd() {
        return sohd;
    }

    public void setSohd(int sohd) {
        this.sohd = sohd;
    }

    public int getMand() {
        return mand;
    }

    public void setMand(int mand) {
        this.mand = mand;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public float getTrigia() {
        return trigia;
    }

    public void setTrigia(float trigia) {
        this.trigia = trigia;
    }
}
