package com.example.user.quancafe.activity.model;

import java.io.Serializable;

/**
 * Created by User on 13/07/2018.
 */

public class Ban implements Serializable {
    private int stt;
    private int trangthai;

    public Ban(int stt, int trangthai) {
        this.stt = stt;
        this.trangthai = trangthai;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}
