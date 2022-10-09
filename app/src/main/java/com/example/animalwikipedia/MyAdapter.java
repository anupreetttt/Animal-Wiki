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

    private ArrayList<String> listOfAnimals; //data: the names displayed
    private ArrayList<Integer> imageList;
    private ArrayList<String> listOfUrls;
    private RVClickListener RVlistener; //listener defined in main activity
    private int viewOption;
    int imageID;
    int textID;
    Boolean viewCheck;
    protected static final String EXTRA_RES_ID = "POS";

    /*
    passing in the data and the listener defined in the main activity
     */
    public MyAdapter(ArrayList<String> animalList, ArrayList<Integer> myAnimalImg, ArrayList<String> urls, Boolean checkView, RVClickListener listener){
        // save list of names to be displayed passed by main activity
        listOfAnimals = animalList;
        imageList = myAnimalImg;
        listOfUrls = urls;
        viewCheck = checkView;
        if (viewCheck == true) {
            viewOption = R.layout.grid_view;
            imageID = R.id.imageGView;
            textID = R.id.textGView;
        } else {
            viewOption = R.layout.list_view;
            imageID = R.id.imageLView;
            textID = R.id.textLView;
        }
        // save listener defined and passed by main activity
        this.RVlistener = listener;
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

        return new ViewHolder(gridView, RVlistener);

//         create ViewHolder passing the view that it will wrap and the listener on the view
//         create ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // populate the item at the input position
        holder.name.setText(listOfAnimals.get(position));
        holder.image.setImageResource(imageList.get(position));

    }

    @Override
    public int getItemCount() {
        return listOfAnimals.size();
    }

    /*
        This class creates a wrapper object around a view that contains the layout for
         an individual item in the list. It also implements the onClickListener so each ViewHolder in the list is clickable.
        It's onclick method will call the onClick method of the RVClickListener defined in
        the main activity.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

        public TextView name;
        public ImageView image;
        private RVClickListener listener;
        private View itemView;

        public ViewHolder(@NonNull View itemView, RVClickListener passedListener) {
            super(itemView);
            name = (TextView) itemView.findViewById(textID);
            image = (ImageView) itemView.findViewById(imageID);
            this.itemView = itemView;
            itemView.setOnCreateContextMenuListener(this); //set context menu for each list item (long click)
            this.listener = passedListener;

            /* don't forget to set the listener defined here to the view (list item) that was
                passed in to the constructor. */
            itemView.setOnClickListener(this); //set short click listener

        }

        @Override
        public void onClick(View v) {
            // getAdapterPosition() returns the position of the current ViewHolder in the adapter.
            listener.onClick(v, getAdapterPosition());
            Log.i("ON_CLICK", "in the onclick in view holder");
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            //inflate menu from xml

            MenuInflater inflater = new MenuInflater(v.getContext());
            inflater.inflate(R.menu.longclick_menu, menu );
            menu.getItem(0).setOnMenuItemClickListener(viewPhoto);
            menu.getItem(1).setOnMenuItemClickListener(wikipedia);
        }

        /*
            listener for menu items clicked
         */
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