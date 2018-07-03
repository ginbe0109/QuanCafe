package com.example.user.quancafe.activity.model;

import java.io.Serializable;

/**
 * Created by User on 21/01/2018.
 */

public class LoaiMonAn implements Serializable {
    private int idloai;
    private String tenloai;
    private String hinhloai;

    public LoaiMonAn() {
    }

    public LoaiMonAn(int idloai, String tenloai, String hinhloai) {
        this.idloai = idloai;
        this.tenloai = tenloai;
        this.hinhloai = hinhloai;
    }

    public int getIdloai() {
        return idloai;
    }

    public void setIdloai(int idloai) {
        this.idloai = idloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getHinhloai() {
        return hinhloai;
    }

    public void setHinhloai(String hinhloai) {
        this.hinhloai = hinhloai;
    }
}
