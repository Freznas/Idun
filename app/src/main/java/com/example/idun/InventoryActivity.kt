package com.example.idun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
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
//declaring a type to variables from my different files and classes
    listView = findViewById(R.id.lv_Inventory1)

    combinedListDataManager = CombinedListDataManager(this)
    adapter = CombinedListAdapter(
        this,
        0,
        CombinedListDataManager()
    )

    listView.adapter = adapter

    val shoppingList = CombinedListDataManager.getShoppingList()
    adapter.addAll(shoppingList)


//        dataManager = DataManager(this)
    val saveShoppingList = CombinedListDataManager.getShoppingListWithAmounts().map {
        "${it.first}|${it.second}"

    }
}


}