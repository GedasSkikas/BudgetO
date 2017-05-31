package com.example.gedas.budgetingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Gedas on 03/04/2017.
 */

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Setting up the app logo
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.pound_logo);
    }
}
