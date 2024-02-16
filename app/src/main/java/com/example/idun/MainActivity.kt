package com.example.idun

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.idun.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

// declare and initialize buttons to start the intent to open the relevant activity.
        binding.btnShoppingMain.setOnClickListener {
            val intent = Intent(this, ShoppingActivity::class.java)
            startActivity(intent)
        }
        binding.btnInventoryMain.setOnClickListener {
            val intent = Intent(this, InventoryActivity::class.java)
            startActivity(intent)
        }
            binding.btnChatMain.setOnClickListener {
                val intent = Intent(this, ChatActivity::class.java)
                startActivity(intent)
            }

        binding.btnNotesMain.setOnClickListener {
            val intent = Intent(this, NotepadActivity::class.java)
            startActivity(intent)
        }
        }
    }
