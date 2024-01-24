package com.example.idun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.idun.databinding.ActivityChatBinding
import com.example.idun.databinding.ActivityMainBinding

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        chatAdapter = ChatAdapter()
        recyclerView.adapter = chatAdapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Set up Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("chat")

        // Attach a listener to read the data at our chat reference
        databaseReference.addChildEventListener(object : ChildEventListener {
            fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                // A new message is added, handle it
                val message = snapshot.getValue(ChatMessage::class.java)
                if (message != null) {
                    chatAdapter.addMessage(message)
                    // Scroll to the last message
                    recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
                }
            }
        })
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // User signed in successfully
                } else {
                    // If sign-in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun sendMessage(message: ChatMessage) {
        // Push the message to Firebase database
        val databaseReference = FirebaseDatabase.getInstance().getReference("chat")
        databaseReference.push().setValue(message)


    }

}