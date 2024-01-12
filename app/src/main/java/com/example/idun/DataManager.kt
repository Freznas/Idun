package com.example.idun

import android.content.Context
import android.content.SharedPreferences


class DataManager(context: Context) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("IdunSharedPref", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        const val SHOPPING_LIST_KEY = "shopping_list"
        const val ITEM_AMOUNT_PREFIX = "item_amount_"
    }

    fun saveShoppingListItem(itemName: String, itemAmount: String) {
        val shoppingList = getShoppingList().toMutableSet()
        shoppingList.add("$itemName|$itemAmount")
        editor.putStringSet(SHOPPING_LIST_KEY, shoppingList)
        editor.apply()
    }

    fun getShoppingList(): Set<String> {
        return sharedPreferences.getStringSet(SHOPPING_LIST_KEY, setOf()) ?: setOf()
    }

    fun getShoppingListWithAmounts(): List<Pair<String, Int>> {
        val shoppingList = getShoppingList()
        return shoppingList.map { item ->
            val amount = getItemAmount(item)
            item to amount
        }
    }

    fun saveItemAmount(itemName: String, amount: Int) {
        editor.putInt("$ITEM_AMOUNT_PREFIX$itemName", amount)
        editor.apply()
    }

    private fun getItemAmount(itemName: String): Int {
        return sharedPreferences.getInt("$ITEM_AMOUNT_PREFIX$itemName", 0)
    }

    fun deleteItemByitem(itemToDelete: String): Boolean {
        val editor = sharedPreferences.edit()
        val allEntries = sharedPreferences.all

        for ((key, value) in allEntries) {
            val entryValue = value.toString()
            val noteParts = entryValue.split("\\|")
            if (noteParts.isNotEmpty() && noteParts[0] == itemToDelete)
                editor.remove(key)
            return editor.commit()
        }
        return false
    }

}


