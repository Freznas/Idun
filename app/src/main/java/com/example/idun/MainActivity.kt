package com.example.idun
/*  #100DaysOfCode
    Day
    1/100: Struggled to implement the button in previous version 
    2/100: Decided to restart the project and ditch the fragment part for now and work mainly with activites. created the layout for the MainActivity.
*/
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.idun.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var button:Button
   lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


binding.btnShopping.setOnClickListener {
    val intent = Intent(this,ShoppingActivity::class.java)
    startActivity(intent)
}
        binding.btnInventory.setOnClickListener {
            val intent = Intent(this,InventoryActivity::class.java)
            startActivity(intent)
        }
    }
}