package com.example.idun;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.List;

public class CombinedListAdapter extends ArrayAdapter<String>

{
    List<String> items;
    CombinedListDataManager shoppingListDatamanager;

    public CombinedListAdapter(@NonNull Context context, int resource, CombinedListDataManager shoppingListDatamanager)
    {

        super(context, 0);
        this.shoppingListDatamanager = shoppingListDatamanager;

        this.items = new ArrayList<>();
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
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
            TextView amountTextView = ListItemView.findViewById(R.id.tv_itemAmount);

            if (itemParts.length > 0)
            {
                titleTextView.setText(itemParts[0]);
                if (itemParts.length > 1)
                {
                    amountTextView.setText(itemParts[1]);
                } else
                {
                    amountTextView.setText(""); // Handle case when amount is not present
                }
            }

            return ListItemView;
        }
        return ListItemView;
    }

    public void setItems(List<String> itemList)
    {
        this.items = itemList;
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }

//  void removeSelectedItem(String title, String amount) {
//        String selectedItem = title + "|" + amount;
//        deleteItemByTitle(title);
//        Set<String> shoppingList = new HashSet<>(notepadDataManager.getShoppingList());
//        shoppingList.removeIf(item -> item.startsWith(title));
//        shoppingList.remove(selectedItem);
//        notepadDataManager.saveShoppingList(shoppingList);
//        notifyDataSetChanged();
//    }

    void deleteItemByTitle(String titleToDelete)
    {
        for (int i = 0; i < getCount(); i++)
        {
            String currentItem = getItem(i);
            if (currentItem != null)
            {
                String[] itemParts = currentItem.split("\\|");
                if (itemParts.length > 0 && itemParts[0].equals(titleToDelete))
                {
                    shoppingListDatamanager.deleteItemByTitle(titleToDelete);
                    remove(currentItem);
                    notifyDataSetChanged();
                    return;
                }
            }

        }

    }
}






