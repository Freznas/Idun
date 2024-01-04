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
        var itemInShoppingList=String
        var numberOfItemsInList=Int
        //
        val adapter = ShoppingListAdapter(dataManager.getShoppingList().toList(), dataManager)

        shoppingListRecyclerView.layoutManager = layoutManager
        shoppingListRecyclerView.adapter = adapter


// Saving a shopping list item
        dataManager.saveShoppingListItem("Milk")

// Getting the shopping list
        val shoppingList = dataManager.getShoppingList()


        dataManager.saveItemAmount("Milk", 2)

// Getting the amount of an item
        val amountOfMilk = dataManager.getItemAmount("Milk")


        binding.btnAddToList.setOnClickListener(View.OnClickListener {
            val title: String = itemInShoppingList.toString()
            val content: String = numberOfItemsInList.toString()
            if (title.isEmpty()) {
                Toast.makeText(this@ShoppingActivity, R.string."Nothing to save, fill in both fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (editNotePosition != -1) {
                    val isUpdated: Boolean =
                        dataManager.updateNoteByPosition(editNotePosition, title, content)
                    if (isUpdated) {
                        val intent = Intent(this@NotesActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    val newPosition: Int = dataManager.getNotes().size()
                    dataManager.saveNote(
                        newPosition,
                        title,
                        content
                    ) //Troligtvis här problemet ligger, tilldelar endast "Key" 0 om och om igen.
                    val intent = Intent(this@NotesActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        })
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