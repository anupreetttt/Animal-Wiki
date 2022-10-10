package com.example.animalwikipedia;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<String> listOfAnimals; // the animal names displayed
    private ArrayList<Integer> imageList;  // the animals imanges displayed
    private ArrayList<String> listOfUrls; // the animal urls
    private RVClickListener newListener; //listener as defined in main activity
    private int viewOption;
    private int imageID;
    private int textID;
    Boolean viewCheck;  // to check grid or list view
    protected static final String EXTRA_RES_ID = "POS";
    Boolean gridLayout = true;
//    Boolean listLayout = false;

    /*
    passing in the data and the listener defined in the main activity
     */
    public MyAdapter(ArrayList<String> animalList, ArrayList<Integer> myAnimalImg, ArrayList<String> urls, Boolean checkView, RVClickListener listener){
        // save list of names to be displayed passed by main activity
        listOfAnimals = animalList;
        imageList = myAnimalImg;
        listOfUrls = urls;
        viewCheck = checkView;

        // if the layout if gridView, it will fetch the respective files, otherwise it will retrieve list view files and ids
        if (viewCheck == gridLayout) {
            viewOption = R.layout.grid_view;
            imageID = R.id.imageGView;
            textID = R.id.textGView;
        } else {
            viewOption = R.layout.list_view;
            imageID = R.id.imageLView;
            textID = R.id.textLView;
        }
        // save listener defined and passed by main activity
        this.newListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewOption,parent, false));

        Context context = parent.getContext();
        // get inflater and inflate XML layout file
        LayoutInflater inflater = LayoutInflater.from(context);
        View gridView = inflater.inflate(viewOption, parent, false);

        // create ViewHolder passing the view that it will wrap and the listener on the view

        return new ViewHolder(gridView, newListener);

//         create ViewHolder passing the view that it will wrap and the listener on the view
//         create ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // populate the item at the input position
        holder.textView.setText(listOfAnimals.get(position));
        holder.imageView.setImageResource(imageList.get(position));

    }

    @Override
    public int getItemCount() {
        return listOfAnimals.size();
    }

        //The layout for each individual item in the list is contained in a view that is created by this class as a wrapper object. Additionally, the onClickListener is implemented, making each ViewHolder in the list clickable.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

        public ImageView imageView;
        public TextView textView;
        private RVClickListener listener;  // The RVClickListener defined in the main activity's onClick method will be called by this object's onClick function.
        private View view;

        public ViewHolder(@NonNull View itemView, RVClickListener passListener) {
            super(itemView);
            textView = (TextView) itemView.findViewById(textID);
            imageView = (ImageView) itemView.findViewById(imageID);
            this.view = itemView;
            itemView.setOnCreateContextMenuListener(this); //set context menu for each list item (long click)
            this.listener = passListener;
            itemView.setOnClickListener(this);       //set  oncCick listener

        }@Override
        public void onClick(View v) {
            // getAdapterPosition() returns the position of the current ViewHolder in the adapter.
            listener.onClick(v, getAdapterPosition());
            Log.i("OnCLICK", "After clicking on the view holder");
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            //This will inflate the context menu from xml

            MenuInflater inflater = new MenuInflater(v.getContext());
            inflater.inflate(R.menu.longclick_menu, menu );
            menu.getItem(0).setOnMenuItemClickListener(viewPhoto);
            menu.getItem(1).setOnMenuItemClickListener(wikipedia);
        }

        // If user selects the preview photo option from the long click context menu:
        private final MenuItem.OnMenuItemClickListener viewPhoto = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                Intent intent = new Intent(itemView.getContext(), PreviewImage.class);
//                intent.putExtra(EXTRA_RES_ID, imageList.get(getAdapterPosition()));
                intent.putExtra(EXTRA_RES_ID, (int) imageList.get(getAdapterPosition()));
                itemView.getContext().startActivity(intent);
                return true;
            }
        };

        // If users selects the wikipedia option from the long click context menu,  they will be redirected to the respective animal wikipedia page
        private final MenuItem.OnMenuItemClickListener wikipedia = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(listOfUrls.get(getAdapterPosition())));
                itemView.getContext().startActivity(intent);
                return true;
            }
        };
    }
}