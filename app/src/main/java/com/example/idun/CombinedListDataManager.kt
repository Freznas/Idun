package com.example.idun

import android.content.Context
import android.content.SharedPreferences


class CombinedListDataManager(context: Context) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Shared_Combined", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        const val SHOPPING_LIST_KEY = "shopping_list"
        const val INVENTORY_LIST_KEY = "inventory_list"
        const val ITEM_AMOUNT_PREFIX = "item_amount_"
    }



    fun saveInventoryListItem(itemName: String,itemAmount: String)
    {
        saveItem(INVENTORY_LIST_KEY,itemName,itemAmount)
    }
    fun deleteInventoryItemByTitle(title:String?): Boolean{
        return deleteItemByTitle(INVENTORY_LIST_KEY)
    }
    fun getInventoryList(): Set<String>
    {
        return getItems(INVENTORY_LIST_KEY)
    }
    fun getInventoryListWithAmounts(): List<Pair<String, Int>>{
        return getItemsWithAmounts(INVENTORY_LIST_KEY)
    }
    private fun saveItem(listKey: String, itemName: String, itemAmount: String) {
        val itemList = getItems(listKey).toMutableSet()
        itemList.add("$itemName|$itemAmount")
        editor.putStringSet(listKey, itemList)
        editor.apply()
    }

    fun saveItems(listKey: String,items: Set<String>)
    {
        editor.putStringSet(listKey,items)
        editor.apply()
    }


    fun deleteItemByTitle(title: String?): Boolean {
//        return deleteItemByItem(SHOPPING_LIST_KEY,title)
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
    fun saveShoppingListItem(itemName: String, itemAmount: String) {
//        val shoppingList = getShoppingList().toMutableSet()
//        shoppingList.add("$itemName|$itemAmount")
//        editor.putStringSet(SHOPPING_LIST_KEY, shoppingList)
//
//        editor.apply()
        saveItem(SHOPPING_LIST_KEY, itemName, itemAmount)
    }
    fun getShoppingList(): Set<String> {
//        return sharedPreferences.getStringSet(SHOPPING_LIST_KEY, setOf()) ?: setOf()
        return getItems(SHOPPING_LIST_KEY)
    }

    private fun getItems(listKey: String): Set<String> {
        return sharedPreferences.getStringSet(listKey, setOf()) ?: setOf()
    }

    fun getShoppingListWithAmounts(): List<Pair<String, Int>> {
//        val shoppingList = getShoppingList()
//        return shoppingList.map { item ->
//            val amount = getItemAmount(item)
//            item to amount
//        }
        return getItemsWithAmounts(SHOPPING_LIST_KEY)
    }

    private fun getItemsWithAmounts(listKey: String): List<Pair<String, Int>> {
        val itemList = getItems(listKey)
        return itemList.map { item ->
            val amount = getItemAmount(item)
            item to amount
        }
    }

    private fun getItemAmount(itemName: String): Int {
        return sharedPreferences.getInt("$ITEM_AMOUNT_PREFIX$itemName", 0)
    }

//    private fun getItemAmount(itemName: String): Int {
//        return sharedPreferences.getInt("$ITEM_AMOUNT_PREFIX$itemName", 0)
//    }

    //    fun deleteItemByItem(itemToDelete: String): Boolean {
//        val editor = sharedPreferences.edit()
//        val allEntries = sharedPreferences.all
//
//        for ((key, value) in allEntries) {
//            Log.d("AllEntries", "$key - $value")
//            val entryValue = value.toString()
//            val noteParts = entryValue.split("\\|")
//            if (noteParts.isNotEmpty() && noteParts[0] == itemToDelete) {
//                Log.d("DeleteItem", "Removing: $key - $entryValue")
//
//                editor.remove(key)
//            return editor.commit()
//            }
//        }
//        val commitResult = editor.commit()
//        Log.d("CommitResult", "Commit result: $commitResult")
//        return commitResult
//        return editor.commit()
//
//    }
    fun saveShoppingList(shoppingList: Set<String>) {
        editor.putStringSet(SHOPPING_LIST_KEY, shoppingList)
        editor.apply()
    }
}


