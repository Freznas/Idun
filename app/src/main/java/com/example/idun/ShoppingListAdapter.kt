package com.example.idun

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingListAdapter(private val dataManager: DataManager) :
    RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {
    private val items = mutableListOf<String>()
    private val amounts = mutableMapOf<String, Int>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.tv_ItemInList)
        val itemAmountTextView: TextView = itemView.findViewById(R.id.tv_itemAmount)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: String, amount: Int) {
        items.add(item)
        amounts[item] = amount
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listview_shoppinglist, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val amount = amounts[item]

        holder.itemNameTextView.text = item
        holder.itemAmountTextView.text = "$amount" // Show the amount

        holder.itemView.setOnClickListener {
            // Handle item click if needed
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Pair<String, Int>>) {
        this.items.clear()
        this.items.addAll(items.map { "${it.first}.${it.second}" })
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
