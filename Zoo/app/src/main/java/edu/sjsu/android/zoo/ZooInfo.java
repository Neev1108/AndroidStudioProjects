package edu.sjsu.android.zoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ZooInfo extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoo_info);
        Button button1 = findViewById(R.id.button);
        button1.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button){
            String myDial = "tel:888-8888";
            Intent make_call_intent = new Intent(Intent.ACTION_DIAL, Uri.parse(myDial));
            startActivity(make_call_intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.zoo_info:
                Intent zoo_info = new Intent(this, ZooInfo.class);
                startActivity(zoo_info);
                return (true);
            case R.id.uninstall:
                Intent delete = new Intent(Intent.ACTION_DELETE, Uri.parse("package:edu.sjsu.android.zoo"));
                startActivity(delete);
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

}