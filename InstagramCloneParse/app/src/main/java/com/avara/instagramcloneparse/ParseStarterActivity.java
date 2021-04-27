package com.avara.instagramcloneparse;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseConfig;

public class ParseStarterActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId("XD8GJNusmBBEEfqmYJn2wA4J1eo20COYFqAI1gRo")
        .clientKey("yg6jlTpK4iORrPgvL1QyaTJkeQNN7suNvIz3XIkr")
        .server("https://parseapi.back4app.com/")
        .build());

    }
}
