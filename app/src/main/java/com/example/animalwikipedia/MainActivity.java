package com.example.animalwikipedia;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
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
    ArrayList<Integer> myAnimals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        List<String> names = Arrays.asList("Tiger", "Lion", "Zebra", "Panda", "Giraffe", "Elephant", "Rabbit");
        List<Integer> animalImg  = Arrays.asList(R.drawable.tiger, R.drawable.lion, R.drawable.zebra, R.drawable.panda, R.drawable.giraffe, R.drawable.elephant, R.drawable.rabbit);

        animalList = new ArrayList<>();
        animalList.addAll(names);
        myAnimals = new ArrayList<>();
        myAnimals.addAll(animalImg);

        //Define the listener with a lambda and access the list of names with the position passed in
        //  RVClickListener listener = (view, position)-> Toast.makeText(this, "position: "+position, Toast.LENGTH_LONG).show();

        //Define the listener with a lambda and access the name of the list item from the view
        RVClickListener listener = (view, position) -> {
            TextView name = (TextView) view.findViewById(R.id.textView);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            Toast.makeText(this, name.getText(), Toast.LENGTH_SHORT).show();
        };

        MyAdapter adapter = new MyAdapter(animalList, myAnimals, listener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2)); //use this line to see as a grid
//        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //use this line to see as a standard vertical list




    }
/*  //you can put the contextItem selected method here or use a listener in the ViewHolder class
    @Override
    public boolean onContextItemSelected(MenuItem item){
        Log.i("ON_CLICK","menu item clicked");

        return true;
    }
    */

}