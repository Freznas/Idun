//package com.example.idun
//
//import android.content.Context
//import android.content.SharedPreferences
//
//
//
//class InventoryDatamanager(context:Context) {
//
//
//    private var sharedPreferences: SharedPreferences =
//        context.getSharedPreferences("Shared_inventory", Context.MODE_PRIVATE)
//    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
//
//
//    companion object {
//        const val INVENTORY_LIST_KEY = "shopping_list"
//        const val INVENTORY_AMOUNT_PREFIX = "item_amount_"
//    }
//
//    fun saveInventoryListItem(inventoryItem: String, inventoryAmount: String) {
//        val inventoryList = getInventoryList()?.toMutableSet()
//        inventoryList?.add("$inventoryItem |$inventoryAmount")
//        editor.putStringSet(INVENTORY_LIST_KEY, inventoryList)
//
//
//        editor.apply()
//
//    }
//
//    fun getInventoryList(): MutableSet<String>? {
//return sharedPreferences.getStringSet(INVENTORY_LIST_KEY, setOf())
//    }
//}
//
//private fun Any.toMutableSet(): MutableSet<String>? {
//    TODO("Not yet implemented")
//}
//
//private fun Any.map(any: Any): Any {
//    TODO("Not yet implemented")
//
//
//}
