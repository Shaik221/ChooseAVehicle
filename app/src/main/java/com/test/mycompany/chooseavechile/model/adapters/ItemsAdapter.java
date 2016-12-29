package com.test.mycompany.chooseavechile.model.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.mycompany.chooseavechile.R;
import com.test.mycompany.chooseavechile.model.beans.Items;
import com.test.mycompany.chooseavechile.util.OnItemClickListener;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ItemsAdapter extends
        RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<Items> mItems;

    private static OnItemClickListener onItemClickListener;

    // Pass in the contact array into the constructor
    public ItemsAdapter(List<Items> items, OnItemClickListener onItemClickListener) {
        mItems = items;
        this.onItemClickListener = onItemClickListener;

    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView itemTextView;
        View parentView; // The view which holds all the other views

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            // Save the entire itemView, for setting listeners and usch later
            this.parentView = itemView;

            itemView.setClickable(true);

            itemTextView = (TextView) itemView.findViewById(R.id.itemTxt);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.setOnItemClick(v, getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(contactView);

        contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.setOnItemClick(v, viewHolder.getLayoutPosition());
            }
        });


        return viewHolder;
    }

    public void setOnItemClickListener (final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Items items = mItems.get(position);



        TextView textView = viewHolder.itemTextView;
        textView.setText(items.getmKey() +", "+items.getmValue());

        //onItemClickListener.setOnItemClick(textView, position);

      /*  // Set a listener for this entire view
        viewHolder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BASHA","Clicked on row: " + position+"\nvalue is::"+mItems.get(position).getmValue());
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}

