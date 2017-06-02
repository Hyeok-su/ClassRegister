package com.example.pc.classregister;

/**
 * Created by pc on 2017-03-13.
 */

public class Notice {

    String notice;
    String name;
    String date;

    public Notice(String notice, String name, String date) {
        this.notice = notice;
        this.name = name;
        this.date = date;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotice() {
        return notice;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
