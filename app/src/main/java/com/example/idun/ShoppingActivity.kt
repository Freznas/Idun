package com.example.idun

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.idun.databinding.ActivityShoppingBinding

class ShoppingActivity : AppCompatActivity() {
    lateinit var dataManager: DataManager
    lateinit var button: Button
    lateinit var binding: ActivityShoppingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataManager = DataManager(this)
        // Create the adapter and attach it to your RecyclerView
        val shoppingListRecyclerView: RecyclerView = findViewById(R.id.rv_ShoppingList)
        val layoutManager = LinearLayoutManager(this)
        val itemInShoppingList: String = ""
        val numberOfItemsInList: Int = 0

        val adapter = ShoppingListAdapter(dataManager.getShoppingList().toList(), dataManager)
        var editItemInShoppingList = getIntent().getIntExtra("itemPlacedInShoppingList", -1)
        shoppingListRecyclerView.layoutManager = layoutManager
        shoppingListRecyclerView.adapter = adapter

// Saving a shopping list item
        dataManager.saveShoppingListItem("Milk")
// Getting the shopping list
        val shoppingList = dataManager.getShoppingList()
        dataManager.saveItemAmount("Milk", 2)
        val amountOfMilk = dataManager.getItemAmount("Milk")


        binding.btnAddToList.setOnClickListener(View.OnClickListener {
            val title: String = itemInShoppingList.toString()
            val content: String = numberOfItemsInList.toString()
            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(
                    this@ShoppingActivity,
                    R.string.itemPlacedInShoppingList,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            else

            binding.btnRemoveFromList.setOnClickListener {
                val intent = Intent(this, ShoppingActivity::class.java)
                Toast.makeText(this, "You removed Levain", Toast.LENGTH_SHORT).show()
//            startActivity(intent)
            }
            binding.btnHome.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })

    }
}
