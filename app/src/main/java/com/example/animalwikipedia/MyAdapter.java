package com.example.animalwikipedia;

import android.content.Context;
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

import com.example.animalwikipedia.RVClickListener;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
//    public static final int SPAN_COUNT_ONE = 1;
//    public static final int SPAN_COUNT_THREE = 3;
    private ArrayList<String> nameList; //data: the names displayed
    private ArrayList<Integer> imageList;
    private RVClickListener RVlistener; //listener defined in main activity
    // private Context context;
//    private static final int griding = 1;
//    private static final int listing = 2;



    /*
    passing in the data and the listener defined in the main activity
     */
    public MyAdapter(ArrayList<String> theList, ArrayList<Integer> animalList, RVClickListener listener){
        // save list of names to be displayed passed by main activity
        nameList = theList;
        imageList = animalList;

        // save listener defined and passed by main activity
        this.RVlistener = listener;
    }

//    public ItemAdapter(List<Item> items, GridLayoutManager layoutManager) {
//        mItems = items;
//        mLayoutManager = layoutManager;
//    }

//    @Override
//    public int getItemViewType(int position) {
//        int spanCount = mLayoutManager.getSpanCount();
//        if (spanCount == SPAN_COUNT_ONE) {
//            return VIEW_TYPE_BIG;
//        } else {
//            return VIEW_TYPE_SMALL;
//        }
//    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        // get inflater and inflate XML layout file
        LayoutInflater inflater = LayoutInflater.from(context);
        View gridView = inflater.inflate(R.layout.grid_view, parent, false);

        // create ViewHolder passing the view that it will wrap and the listener on the view
        ViewHolder viewHolder = new ViewHolder(gridView, RVlistener); // create ViewHolder

        return viewHolder;

        // create ViewHolder passing the view that it will wrap and the listener on the view
 // create ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // populate the item at the input position
        holder.name.setText(nameList.get(position));
        holder.image.setImageResource(imageList.get(position));

    }

    @Override
    public int getItemCount() {
        return nameList.size();
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
            name = (TextView) itemView.findViewById(R.id.textView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
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
            inflater.inflate(R.menu.context_menu, menu );
            menu.getItem(0).setOnMenuItemClickListener(onMenu);
            menu.getItem(1).setOnMenuItemClickListener(onMenu);


          /*  //create menu in code

            menu.setHeaderTitle("My context menu");

            //add menu items and set the listener for each
            menu.add(0,v.getId(),0,"option 1").setOnMenuItemClickListener(onMenu);
            menu.add(0,v.getId(),0,"option 2").setOnMenuItemClickListener(onMenu);
            menu.add(0,v.getId(),0,"option 3").setOnMenuItemClickListener(onMenu);
            */

        }

        /*
            listener for menu items clicked
         */
        private final MenuItem.OnMenuItemClickListener onMenu = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                Log.i("ON_CLICK", name.getText() + " adapter pos: " + getAdapterPosition());
                return true;
            }
        };



    }
}
