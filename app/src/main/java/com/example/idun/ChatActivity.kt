package com.example.idun

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.idun.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    private lateinit var databaseReference: DatabaseReference // Declare DatabaseReference
    private val firebaseDatabase: FirebaseDatabase =FirebaseDatabase.getInstance() // Initialize FirebaseDatabase
    private lateinit var adapter: ChatAdapter
    private lateinit var dataManager: ChatDataManager
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)



        dataManager = FireBaseDataManager()

        adapter = ChatAdapter(this)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Set up Firebase database reference
        databaseReference = firebaseDatabase.getReference("chat")

        // Attach a listener to read the data at our chat reference
        databaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                // A new message is added, handle it
                val message = snapshot.getValue(ChatMessage::class.java)
                if (message != null) {
                    ChatAdapter.addMessage(message)
                    // Scroll to the last message
                    recyclerView.scrollToPosition(adapter.itemCount - 1)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        FirebaseAuth.getInstance().signInWithEmailAndPassword("joakim198904@live.se", "joakim1234")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // User signed in successfully
                } else {
                    // If sign-in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

}