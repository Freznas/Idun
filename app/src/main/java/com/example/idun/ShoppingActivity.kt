package com.example.idun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.idun.databinding.ActivityInventoryBinding
import com.example.idun.databinding.ActivityMainBinding
import com.example.idun.databinding.ActivityShoppingBinding

class ShoppingActivity : AppCompatActivity() {
    lateinit var button: Button
    lateinit var binding: ActivityShoppingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnAddToList.setOnClickListener {
            val intent = Intent(this, ShoppingActivity::class.java)
            Toast.makeText(this, "You Added Pineapples!", Toast.LENGTH_SHORT).show()
//            startActivity(intent)
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