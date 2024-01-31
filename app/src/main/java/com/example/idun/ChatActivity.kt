package com.example.idun

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.idun.databinding.ActivityChatBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    private lateinit var databaseReference: DatabaseReference // Declare DatabaseReference
    private val firebaseDatabase: FirebaseDatabase =
        FirebaseDatabase.getInstance() // Initialize FirebaseDatabase
    private lateinit var adapter: ChatAdapter
    private lateinit var dataManager: ChatDataManager
    private lateinit var messageEditText: EditText


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataManager = FireBaseDataManager()

        adapter = ChatAdapter(this)
        fetchMessagesFromBackEnd()


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        messageEditText = findViewById(R.id.messageEditText) as EditText

        // Set up Firebase database reference
        databaseReference = firebaseDatabase.getReference("chat")

        val auth = FirebaseAuth.getInstance()

// Sign in anonymously
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign-in success
                    val user = auth.currentUser
                    // Now you can access authenticated features or interact with Firebase services as needed
                } else {
                    // If sign-in fails, display a message to the user.
                    // Log.w(TAG, "signInAnonymously:failure", task.exception)
                    // Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }


            }

        fun sendMessageToFirebase(message: ChatMessage) {
            val databaseReference = FirebaseDatabase.getInstance().getReference("chat")
            databaseReference.push().setValue(message)
        }

        // Function to fetch messages from Firebase
        fun fetchMessagesFromFirebase() {
            val databaseReference = FirebaseDatabase.getInstance().getReference("chat")
            databaseReference.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val message = snapshot.getValue(ChatMessage::class.java)
                    if (message != null) {
                        // Add the message to your RecyclerView adapter's dataset
                        adapter.addMessage(message)
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

                // Implement other ChildEventListener methods as needed
            })
        }

        binding.sendButton.setOnClickListener {
            val messageText = messageEditText.text.toString()
            if (messageText.isNotEmpty()) {
                val currentUser = FirebaseAuth.getInstance().currentUser
                val userId = currentUser?.uid ?: "anonymous"
                val message = ChatMessage(userId, messageText, System.currentTimeMillis())
                sendMessageToFirebase(message)
                messageEditText.text = null
            }
        }

        // Attach a listener to read the data at our chat reference
        databaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
                // A new message is added, handle it
                val message = snapshot.getValue(ChatMessage::class.java)
                if (message != null) {
                    adapter.addMessage(message)
                    // Scroll to the last message
                    recyclerView.scrollToPosition(adapter.itemCount - 1)
                }
            }


            override fun onChildChanged(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
                TODO("enter logic to alter sent messages")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("enter logic to remove sent messages")
            }

            override fun onChildMoved(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
                TODO("enter logic to alter position(Pining or sorting options)")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }

    private fun fetchMessagesFromBackEnd() {
        // Assuming you fetch messages from the backend here and store them in a variable
        val messagesFromBackend: List<ChatMessage> = listOf(
            ChatMessage("Sender1", "Hello!", System.currentTimeMillis()),
            ChatMessage("Sender2", "Hi there!", System.currentTimeMillis() - 1000),
            // Add more messages here as needed
        )

        // Now you can use the fetched messages in your RecyclerView adapter
        adapter.setMessages(messagesFromBackend)
    }


}



