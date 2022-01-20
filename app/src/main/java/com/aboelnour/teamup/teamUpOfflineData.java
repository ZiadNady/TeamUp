package com.aboelnour.teamup;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class teamUpOfflineData extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }

}
