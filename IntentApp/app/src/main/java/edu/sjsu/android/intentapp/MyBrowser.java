package edu.sjsu.android.intentapp;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

public class MyBrowser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_browser);
        TextView text = findViewById(R.id.text);
        String url = getIntent().getDataString();

        if (url != null){
            text.setText(url);
        }

    }
}