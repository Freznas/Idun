package com.example.idun

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.idun.databinding.ActivityShoppingBinding

class ShoppingActivity : AppCompatActivity() {
    lateinit var dataManager: DataManager
    lateinit var binding: ActivityShoppingBinding
    lateinit var listView: ListView
    lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataManager = DataManager(this)

        listView = findViewById(R.id.rv_ShoppingList)

        val adapter = ShoppingListAdapter(this, 0)

        listView.adapter = adapter

        val shoppingList = dataManager.getShoppingList()
        adapter.addAll(shoppingList)

        dataManager = DataManager(this)
        val saveShoppingList = dataManager.getShoppingListWithAmounts().map {
            "${it.first}|${it.second}"
        }
        adapter.setItems(saveShoppingList)
        adapter.context

// Getting the shopping list


        binding.btnAddToList.setOnClickListener {
            val title: String = binding.etItemToPlace.text.toString().trim()
            val itemAmount: String = binding.etAmountToPlace.text.toString()

            if (title.isEmpty() && itemAmount.isEmpty()) {
                val intent = Intent(this, ShoppingActivity::class.java)

                Toast.makeText(this, "Enter Item and amount", Toast.LENGTH_SHORT).show()

            } else {
                val amount = itemAmount.toIntOrNull() ?: 0


                binding.etItemToPlace.text.clear()
                binding.etAmountToPlace.text.clear()
                dataManager.saveShoppingListItem(title, itemAmount)
                Toast.makeText(
                    this@ShoppingActivity,
                    "Item added to the list: $title,  $amount",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }


        binding.btnRemoveFromList.setOnClickListener {
            val intent = Intent(this, ShoppingActivity::class.java)
            Toast.makeText(this, "You removed Levain", Toast.LENGTH_SHORT).show()
        }

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}





