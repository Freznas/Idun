package com.example.idun;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShoppingListAdapter extends ArrayAdapter<String>

{
    private DataManager dataManager;


    public ShoppingListAdapter(@NonNull Context context, int resource)
    {
        super(context, 0);
        this.dataManager = dataManager;

    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View ListItemView = convertView;
        if (ListItemView == null)
        {
            ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.listview_shoppinglist, parent, false);

        }
        String currentItem = getItem(position);
        if (currentItem != null)
        {
            final String[] itemParts = currentItem.split("\\|");
            TextView titleTextView = ListItemView.findViewById(R.id.tv_ItemInList);
            if (itemParts.length > 0)
            {
                titleTextView.setText(itemParts[0]);

            }
            final String itemToDelete = itemParts[0];
            ListItemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent editIntent = new Intent(getContext(), ShoppingActivity.class);
                    editIntent.putExtra("edit_item_position", position);
                    getContext().startActivity(editIntent);
                }
            });
            return ListItemView;
        }
        return ListItemView;
    }
}

public void deleteItemAtPosition (int position)
{
    if (position> 0 && position< getCount())
    {
        String itemToDelete = getItem(position);
        if(itemToDelete!=null)
        {
            String[] itemParts = itemToDelete.split ("\\|");
            if(itemParts.length >0)
            {
                String itemToDelete = itemParts[0];
                dataManager.deleteItemByItem(itemToDeleteToDelete);
                remove(noteToDelete);


        }
    }
}
}
}