package com.example.animalwikipedia;

import static com.example.animalwikipedia.MyAdapter.SPAN_COUNT_ONE;
import static com.example.animalwikipedia.MyAdapter.SPAN_COUNT_THREE;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    ArrayList<String> urls;
//    private GridLayoutManager gridLayoutManager;
//    private MyAdapter itemAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        List<String> names = Arrays.asList("Tiger", "Lion", "Zebra", "Panda", "Giraffe", "Elephant", "Rabbit");
        List<Integer> animalImg  = Arrays.asList(R.drawable.tiger, R.drawable.lion, R.drawable.zebra, R.drawable.panda, R.drawable.giraffe, R.drawable.elephant, R.drawable.rabbit);
        List<String> animalUrls = Arrays.asList("https://en.wikipedia.org/wiki/Tiger", "https://en.wikipedia.org/wiki/Lion",
                "https://en.wikipedia.org/wiki/Zebra", "https://en.wikipedia.org/wiki/Giant_panda",
                "https://en.wikipedia.org/wiki/Giraffe", "https://en.wikipedia.org/wiki/Elephant",
                "https://en.wikipedia.org/wiki/Rabbit");
        animalList = new ArrayList<>();
        animalList.addAll(names);
        myAnimals = new ArrayList<>();
        myAnimals.addAll(animalImg);
        urls = new ArrayList<>();
        urls.addAll(animalUrls);

        //Define the listener with a lambda and access the list of names with the position passed in
        //  RVClickListener listener = (view, position)-> Toast.makeText(this, "position: "+position, Toast.LENGTH_LONG).show();

        //Define the listener with a lambda and access the name of the list item from the view
        RVClickListener listener = (view, position) -> {
            TextView name = (TextView) view.findViewById(R.id.textView);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            Toast.makeText(this, name.getText(), Toast.LENGTH_SHORT).show();

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(animalUrls.get(position)));
                }
            });
        };
//        gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT_ONE);

        MyAdapter adapter = new MyAdapter(animalList, myAnimals, listener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2)); //use this line to see as a grid
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //use this line to see as a standard vertical list

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                Toast.makeText(this, "Grid view", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu2:
                Toast.makeText(this, "List view", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu1) {
//            switchLayout();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//    private void switchLayout() {
//        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_ONE) {
//            gridLayoutManager.setSpanCount(SPAN_COUNT_THREE);
//        } else {
//            gridLayoutManager.setSpanCount(SPAN_COUNT_ONE);
//        }
//        itemAdapter.notifyItemRangeChanged(0, itemAdapter.getItemCount());
//    }
}