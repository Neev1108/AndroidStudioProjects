package edu.sjsu.android.intentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.net.Uri;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

            if (v.getId() == R.id.button1){
                String webpage_link = "http://www.amazon.com";
                Uri webpage = Uri.parse(webpage_link);
                Intent webpage_intent = new Intent(Intent.ACTION_VIEW, webpage);
                String chooser_title = (String) getResources().getText(R.string.chooser_title);
                Intent chooser = Intent.createChooser(webpage_intent, chooser_title);

                startActivity(webpage_intent);

            }

            if (v.getId() == R.id.button2){
                String myDial = "tel:+194912344444";
                Intent make_call_intent = new Intent(Intent.ACTION_DIAL, Uri.parse(myDial));
                startActivity(make_call_intent);
            }

        }
    }
