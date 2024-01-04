package com.example.idun

import android.content.Context
import android.content.SharedPreferences

class DataManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        const val SHOPPING_LIST_KEY = "shopping_list"
        const val ITEM_AMOUNT_PREFIX = "item_amount_"
    }

    fun saveShoppingListItem(itemName: String) {
        val shoppingList = getShoppingList().toMutableSet()
        shoppingList.add(itemName)
        editor.putStringSet(SHOPPING_LIST_KEY, shoppingList)
        editor.apply()
    }

    fun getShoppingList(): Set<String> {
        return sharedPreferences.getStringSet(SHOPPING_LIST_KEY, setOf()) ?: setOf()
    }

    fun saveItemAmount(itemName: String, amount: Int) {
        editor.putInt("$ITEM_AMOUNT_PREFIX$itemName", amount)
        editor.apply()
    }

    fun getItemAmount(itemName: String): Int {
        return sharedPreferences.getInt("$ITEM_AMOUNT_PREFIX$itemName", 0)
    }

    // Other methods for removing items, clearing the list, etc.
}


