package com.example.pexam.model;

public class TimeCountDown{
    long minute;
    long second;

    public long getMinute() {
        return minute;
    }

    public void setMinute(long minute) {
        this.minute = minute;
    }

    public long getSecond() {
        return second;
    }

    public void setSecond(long second) {
        this.second = second;
    }

    public TimeCountDown(long minute, long second) {
        this.minute = minute;
        this.second = second;
    }
}
