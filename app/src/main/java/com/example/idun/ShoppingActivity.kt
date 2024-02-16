package com.example.idun

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.idun.databinding.ActivityShoppingBinding

// Problem: Come up with a solution to refresh the ListView without having to exit the activity and reenter it.

class ShoppingActivity : AppCompatActivity() {
    private lateinit var combinedListDataManager: CombinedListDataManager
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var listView: ListView
    private lateinit var adapter: CombinedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        listView = findViewById(R.id.lv_ShoppingList)

        combinedListDataManager = CombinedListDataManager(this)
        adapter = CombinedListAdapter(
            this,
            0,
            combinedListDataManager
        )

        listView.adapter = adapter

        val shoppingList = combinedListDataManager.getShoppingList()
        adapter.addAll(shoppingList)


//        notepadDataManager = NotepadDataManager(this)
        val saveShoppingList = combinedListDataManager.getShoppingListWithAmounts().map {
            "${it.first}|${it.second}"
        }
        adapter.setItems(saveShoppingList)
//        adapter.context

        // User defined method to shuffle the array list items
        binding.btnAddToListShopping.setOnClickListener {
            val title: String = binding.etItemToPlaceShopping.text.toString().trim()
            val itemAmount: String = binding.etAmountToPlaceShopping.text.toString()

            if (title.isEmpty() || itemAmount.isEmpty()) {
                Toast.makeText(this, "Enter Item and amount", Toast.LENGTH_SHORT).show()

            } else {
                val amount = itemAmount.toIntOrNull() ?: 0


                binding.etItemToPlaceShopping.text.clear()
                binding.etAmountToPlaceShopping.text.clear()
                combinedListDataManager.saveShoppingListItem(title, itemAmount)


                runOnUiThread {
                    val newData =
                        combinedListDataManager.getShoppingListWithAmounts().toMutableList()

                    newData.shuffle()
                    adapter.notifyDataSetChanged()

                }
                adapter.setItems(combinedListDataManager.getShoppingList().toMutableList())

                combinedListDataManager.saveShoppingListItem(title, itemAmount)
                Toast.makeText(
                    this@ShoppingActivity,
                    " $amount x , $title has been added to the list ",
                    Toast.LENGTH_SHORT

                )
                    .show()

            }
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem: String = adapter.getItem(position)?: ""

            val itemParts = selectedItem.split("\\|".toRegex()).toTypedArray()
            val itemName = if (itemParts.isNotEmpty()) itemParts[0] else ""
            adapter.remove(selectedItem)
            adapter.notifyDataSetChanged()
            Toast.makeText(
                this,
                "you have Removed $itemName, from the Shopping list",
                Toast.LENGTH_LONG
            ).show()
        }


        binding.btnRemoveFromListShopping.setOnClickListener {
            adapter.clear()
            adapter.notifyDataSetChanged()
            binding.etItemToPlaceShopping.setText("")
            binding.etAmountToPlaceShopping.setText("")

            Toast.makeText(this,
                "Your Shopping list has been cleared.",
                Toast.LENGTH_LONG).show()

        }


    }
}




