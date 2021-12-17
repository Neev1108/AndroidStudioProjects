package edu.sjsu.android.zoo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//Recycler view list

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<Animal> animals = new ArrayList<Animal>();

        ImageView lion = new ImageView(this);
        ImageView zebra = new ImageView(this);
        ImageView snake = new ImageView(this);
        ImageView wolf = new ImageView(this);
        ImageView giraffe = new ImageView(this);

        lion.setImageResource(R.drawable.lion);
        zebra.setImageResource(R.drawable.zebra);
        snake.setImageResource(R.drawable.snake);
        wolf.setImageResource(R.drawable.wolf);
        giraffe.setImageResource(R.drawable.giraffe);

        animals.add(new Animal("Lion", R.drawable.lion, "The King of the Savannah known " +
                "for its mane in males and " +
                "living in a pride. This animal is a carnivore that hunts wildebeests, " +
                "gazelle, and zebras."));
        animals.add(new Animal("Zebra", R.drawable.zebra, "This animal (known for its beautiful stripes) are " +
                "herbivores, that live in the African Savannah. They are fairly passive animals." +
                ""));
        animals.add(new Animal("Snake", R.drawable.snake, "These reptiles are cold-blooded and have a variety of species. " +
                "Most hunt small animals like mice."));
        animals.add(new Animal("Wolf", R.drawable.wolf, "These animals are known to hunt in packs. The pack is normally headed by an alpha. " +
                "In the North American wilderness, they primarily hunt deer"));
        animals.add(new Animal("Giraffe", R.drawable.giraffe, "These animals are known for their long necks, which is adapted" +
                "to eat leaves from taller trees. They mainly roam in African Savannah. One of the largest land mammals on Earth."));


        mAdapter = new MyAdapter(this,animals);
        recyclerView.setAdapter(mAdapter);

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
