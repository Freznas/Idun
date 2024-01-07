package com.example.idun

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.idun.databinding.ActivityShoppingBinding

class ShoppingActivity : AppCompatActivity() {
    lateinit var dataManager: DataManager
    lateinit var binding: ActivityShoppingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataManager = DataManager(this)
        // Create the adapter and attach it to your RecyclerView
        val shoppingListRecyclerView: RecyclerView = findViewById(R.id.rv_ShoppingList)
        val layoutManager = LinearLayoutManager(this)

        val adapter = ShoppingListAdapter(dataManager)
        shoppingListRecyclerView.layoutManager = layoutManager
        shoppingListRecyclerView.adapter = adapter
        val savedShoppingList = dataManager.getShoppingListWithAmounts()
        adapter.setItems(savedShoppingList)
// Saving a shopping list item

// Getting the shopping list


        binding.btnAddToList.setOnClickListener {
            val title: String = binding.etItemToPlace.text.toString().trim()
            val content: String = binding.etAmountToPlace.text.toString().trim()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val amount = content.toIntOrNull() ?: 0

                adapter.addItem(title, amount)
                binding.etItemToPlace.text.clear()
                binding.etAmountToPlace.text.clear()
                dataManager.saveShoppingListItem(title, content)
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
//            startActivity(intent)
        }

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}




