package com.example.pexam.application;

import android.app.Application;

public class ApplicationConfig extends Application {
    public static int numAnswered = 0;
    public static String NAME_SQLITE = "PExamSQLite.sqlite";
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
