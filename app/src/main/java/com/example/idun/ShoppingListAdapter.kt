package com.example.idun

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingListAdapter(private val items: List<String>, private val dataManager: DataManager) :
    RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.tv_ItemInList)
        val itemAmountTextView: TextView = itemView.findViewById(R.id.tv_itemAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listview_shoppinglist, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        // Set item name
        holder.itemNameTextView.text = item

        // Retrieve and set item amount from DataManager
        val amount = dataManager.getItemAmount(item)
        holder.itemAmountTextView.text = "Amount: $amount" // Show the amount, modify this based on your needs

        // Set any click listeners or other interactions if needed
        // For example, edit or remove item functionality
        holder.itemView.setOnClickListener {
            // Handle item click if needed
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
