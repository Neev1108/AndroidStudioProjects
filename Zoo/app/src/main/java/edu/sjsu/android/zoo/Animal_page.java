package edu.sjsu.android.zoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class Animal_page extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_page);
        String image = getIntent().getStringExtra("animal_image");
        String name = getIntent().getStringExtra("animal_name");
        String description = getIntent().getStringExtra("animal_description");


        TextView nameView = findViewById(R.id.textView3);
        TextView descriptionView = findViewById((R.id.textView4));
        ImageView imageView = findViewById(R.id.image);

        nameView.setText(name);
        descriptionView.setText(description);
        imageView.setImageResource(Integer.parseInt(image));
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
                Intent delete = new Intent(Intent.ACTION_DELETE);
                delete.setData(Uri.parse("package:edu.sjsu.android.zoo"));
                startActivity(delete);
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }
}