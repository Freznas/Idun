package com.example.idun

import android.content.Context
import android.content.SharedPreferences
import android.util.Log


class CombinedListDataManager(context: Context) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Shared_shopping", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        const val SHOPPING_LIST_KEY = "shopping_list"
        const val INVENTORY_LIST_KEY = "inventory_list"
        const val ITEM_AMOUNT_PREFIX = "item_amount_"
    }

    fun saveShoppingListItem(itemName: String, itemAmount: String) {
        val shoppingList = getShoppingList().toMutableSet()
        shoppingList.add("$itemName|$itemAmount")
        editor.putStringSet(SHOPPING_LIST_KEY, shoppingList)

        editor.apply()
    }


    fun deleteItemByTitle(title: String?): Boolean {
        val editor = sharedPreferences.edit()
        val allEntries = sharedPreferences.all
        for ((key, value) in allEntries) {
            val entryValue = value.toString()
            val itemParts =
                entryValue.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (itemParts.isNotEmpty() && itemParts[0] == title) {
                editor.remove(key)
                editor.apply()
                return true

            }
        }
        return false
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

    private fun getItemAmount(itemName: String): Int {
        return sharedPreferences.getInt("$ITEM_AMOUNT_PREFIX$itemName", 0)
    }

    fun deleteItemByItem(itemToDelete: String): Boolean {
        val editor = sharedPreferences.edit()
        val allEntries = sharedPreferences.all

        for ((key, value) in allEntries) {
            Log.d("AllEntries", "$key - $value")
            val entryValue = value.toString()
            val noteParts = entryValue.split("\\|")
            if (noteParts.isNotEmpty() && noteParts[0] == itemToDelete) {
                Log.d("DeleteItem", "Removing: $key - $entryValue")

                editor.remove(key)
//            return editor.commit()
            }
        }
        val commitResult = editor.commit()
        Log.d("CommitResult", "Commit result: $commitResult")
        return commitResult
//        return editor.commit()

    }
    fun saveShoppingList(shoppingList: Set<String>) {
        editor.putStringSet(SHOPPING_LIST_KEY, shoppingList)
        editor.apply()
    }
}


