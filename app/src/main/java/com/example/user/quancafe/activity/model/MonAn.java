package com.example.user.quancafe.activity.model;

import java.io.Serializable;

/**
 * Created by User on 11/07/2018.
 */

public class MonAn implements Serializable {
    private int maMon;
    private int maloaiMon;
    private String tenMon;
    private String hinhMon;
    private String motaMon;
    private float dongia;

    public MonAn() {
    }

    public MonAn(int maMon, int maloaiMon, String tenMon, String hinhMon, String motaMon, float dongia) {
        this.maMon = maMon;
        this.maloaiMon = maloaiMon;
        this.tenMon = tenMon;
        this.hinhMon = hinhMon;
        this.motaMon = motaMon;
        this.dongia = dongia;
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public int getMaloaiMon() {
        return maloaiMon;
    }

    public void setMaloaiMon(int maloaiMon) {
        this.maloaiMon = maloaiMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getHinhMon() {
        return hinhMon;
    }

    public void setHinhMon(String hinhMon) {
        this.hinhMon = hinhMon;
    }

    public String getMotaMon() {
        return motaMon;
    }

    public void setMotaMon(String motaMon) {
        this.motaMon = motaMon;
    }

    public float getDongia() {
        return dongia;
    }

    public void setDongia(float dongia) {
        this.dongia = dongia;
    }
}
