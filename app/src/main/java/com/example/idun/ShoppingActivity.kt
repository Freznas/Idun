package com.example.idun

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.idun.databinding.ActivityShoppingBinding

// Problem: Come up with a solution to refresh the ListView without having to exit the activity and reenter it.

class ShoppingActivity : AppCompatActivity() {
    private lateinit var dataManager: DataManager
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        listView = findViewById(R.id.rv_ShoppingList)

        dataManager = DataManager(this)
        val adapter = ShoppingListAdapter(this, 0)

        listView.adapter = adapter

        val shoppingList = dataManager.getShoppingList()
        adapter.addAll(shoppingList)


//        dataManager = DataManager(this)
        val saveShoppingList = dataManager.getShoppingListWithAmounts().map {
            "${it.first}|${it.second}"
        }
        adapter.setItems(saveShoppingList)
        adapter.context

        // User defined method to shuffle the array list items
        binding.btnAddToList.setOnClickListener {
            val title: String = binding.etItemToPlace.text.toString().trim()
            val itemAmount: String = binding.etAmountToPlace.text.toString()

            if (title.isEmpty() || itemAmount.isEmpty()) {

                Toast.makeText(this, "Enter Item and amount", Toast.LENGTH_SHORT).show()

            } else {
                val amount = itemAmount.toIntOrNull() ?: 0


                binding.etItemToPlace.text.clear()
                binding.etAmountToPlace.text.clear()
                dataManager.saveShoppingListItem(title, itemAmount)


                runOnUiThread {
                    val newData = dataManager.getShoppingListWithAmounts().toMutableList()

                    newData.shuffle()
                    adapter.notifyDataSetChanged()

                }
                adapter.setItems(dataManager.getShoppingList().toMutableList())

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
            Intent(this, ShoppingActivity::class.java)
            Toast.makeText(this, "You removed Levain", Toast.LENGTH_SHORT).show()
        }

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    }







