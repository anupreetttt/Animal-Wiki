package com.example.animalwikipedia;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> animalList;
    ArrayList<Integer> myAnimalImg;
    ArrayList<String> urls;
    RVClickListener listener;
    private Boolean checkGridView = true;
    private Boolean checkListView = false;
    private Boolean checkView = checkGridView;
    RecyclerView recyclerView;
    private static final String layout = "LAYOUT";

    List<String> names = Arrays.asList("Tiger", "Lion", "Zebra", "Panda", "Giraffe", "Elephant", "Rabbit");
    List<Integer> animalImg = Arrays.asList(R.drawable.tiger, R.drawable.lion, R.drawable.zebra, R.drawable.panda, R.drawable.giraffe, R.drawable.elephant, R.drawable.rabbit);
    List<String> animalUrls = Arrays.asList("https://en.wikipedia.org/wiki/Tiger", "https://en.wikipedia.org/wiki/Lion",
            "https://en.wikipedia.org/wiki/Zebra", "https://en.wikipedia.org/wiki/Giant_panda",
            "https://en.wikipedia.org/wiki/Giraffe", "https://en.wikipedia.org/wiki/Elephant",
            "https://en.wikipedia.org/wiki/Rabbit");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animalList = new ArrayList<>();
        animalList.addAll(names);
        myAnimalImg = new ArrayList<>();
        myAnimalImg.addAll(animalImg);
        urls = new ArrayList<>();
        urls.addAll(animalUrls);

        recyclerView = findViewById(R.id.recycler_view);
        //Define the listener with a lambda and access the list of names with the position passed in
        //  RVClickListener listener = (view, position)-> Toast.makeText(this, "position: "+position, Toast.LENGTH_LONG).show();

        //Define the listener with a lambda and access the name of the list item from the view
        listener = (view, position) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls.get(position)));
            startActivity(intent);
        };

//        gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT_ONE);
        MyAdapter adapter = new MyAdapter(animalList, myAnimalImg, urls, checkView, listener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        if (checkView == checkGridView) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); //use this line to see as a grid
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this)); //use this line to see as a standard vertical list
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu2:
                switchToGridLayout();
                return true;

            case R.id.menu1:
                switchToListLayout();
                return true;

            default:
                return false;
        }
    }
    private void switchToGridLayout() {
        if (checkView != checkListView) {
            checkView = checkListView;
            Toast.makeText(this, "List View", Toast.LENGTH_SHORT).show();
            MyAdapter adapter = new MyAdapter(animalList, myAnimalImg, urls, false, listener);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            Toast.makeText(this, "List view", Toast.LENGTH_SHORT).show();
        }
    }

    private void switchToListLayout() {
        if (checkView != checkGridView) {
            checkView = checkGridView;
            Toast.makeText(this, "Grid View", Toast.LENGTH_SHORT).show();
            MyAdapter adapter = new MyAdapter(animalList, myAnimalImg, urls, true, listener);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            Toast.makeText(this, "Grid view", Toast.LENGTH_SHORT).show();
        }
    }
}