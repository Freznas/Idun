package com.example.idun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.example.idun.databinding.ActivityInventoryBinding

class InventoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInventoryBinding
    private lateinit var combinedListDataManager: CombinedListDataManager
    private lateinit var listView: ListView
    private lateinit var adapter: CombinedListAdapter

    //Declare Variables for the Activity, getting them from respectable source (adapters, Managers,etc..)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listView = findViewById(R.id.lv_InventoryList)

        combinedListDataManager = CombinedListDataManager(this)
        adapter = CombinedListAdapter(
            this, 0, combinedListDataManager
        )

        listView.adapter = adapter

        val shoppingList = combinedListDataManager.getShoppingList()
        adapter.addAll(shoppingList)

        val saveShoppingList = combinedListDataManager.getShoppingListWithAmounts().map {
            "${it.first}|${it.second}"
        }

        adapter.setItems(saveShoppingList)

        binding.btnEditInventory.setOnClickListener {
            val title: String = binding.etItemPlaceInventory.text.toString().trim()
            val itemAmount: String = binding.etAmountPlaceInventory.text.toString()

            if (title.isEmpty() || itemAmount.isEmpty()) {
                Toast.makeText(this, "Enter item and amount", Toast.LENGTH_LONG).show()

            } else {
                val amount = itemAmount.toIntOrNull() ?: 0

                combinedListDataManager.saveShoppingListItem(title, itemAmount)

                val newData = combinedListDataManager.getShoppingListWithAmounts().toMutableList()
                newData.shuffle()
                adapter.setItems(newData.map { "${it.first}|${it.second}" })

                binding.etItemPlaceInventory.text.clear()
                binding.etAmountPlaceInventory.text.clear()

                Toast.makeText(
                    this@InventoryActivity,
                    "$amount x, $title  has been added to the list",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
        fun removeSelectedItem(title: String, amount: String): Boolean {
            val selectedItem = "$title|$amount"
            adapter.deleteItemByTitle(title)
            val shoppingList = combinedListDataManager.getShoppingList().toMutableSet()
            shoppingList.removeIf { it.startsWith(title) }
            shoppingList.remove(selectedItem)
            combinedListDataManager.saveShoppingList(shoppingList)
            adapter.notifyDataSetChanged()
            return true
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem: String = adapter.getItem(position) ?: ""

            // Split the selectedItem to get title and amount
            val itemParts = selectedItem.split("\\|".toRegex()).toTypedArray()

            // Check if there are at least two parts (title and amount)
            if (itemParts.size >= 2) {
                val title = itemParts[0]
                val amount = itemParts[1]
                // Set the values in your EditText fields
//                binding.etItemToPlace.setText(title) use to place clicked item in edittext
//                binding.etAmountToPlace.setText(amount) use to place clicked item amount in edittext
                adapter.notifyDataSetChanged()
                if (removeSelectedItem(title, amount)) {
                    val toastMessage = "you have removed: $title."
                    Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()

                }
            }


        }


    }
}
