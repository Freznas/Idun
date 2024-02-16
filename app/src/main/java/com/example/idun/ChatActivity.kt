package com.example.idun
// My Activity to handle the functions and communications for my Chat function.
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.idun.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ChatActivity : AppCompatActivity() {

    //initializing and declaration of my variables
    lateinit var binding: ActivityChatBinding
    private lateinit var databaseReference: DatabaseReference // Declare DatabaseReference
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance() // Initialize FirebaseDatabase
    private lateinit var adapter: ChatAdapter
    private lateinit var dataManager: ChatDataManager
    private lateinit var messageEditText: EditText
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

// setting datamanager as firebaseDataManager.
        dataManager = FireBaseDataManager(this)
        recyclerView = findViewById(R.id.rv_ChatmessageList)
        adapter = ChatAdapter(this)
        //Call my function to fetch messages from the backend storage
        fetchMessagesFromBackEnd()
        fetchMessagesFromFirebase()

        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        messageEditText = findViewById(R.id.et_ChatMessage) as EditText

        // Set up Firebase database reference
        databaseReference = firebaseDatabase.getReference("chat")

        val auth = FirebaseAuth.getInstance()

// Sign in anonymously
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // successfull sign in
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "Authentication successfull", Toast.LENGTH_LONG)
                        .show()
                    // Now you can access authenticated features or interact with Firebase services as needed
                } else {
                    // If sign-in fails, display a message to the user.
                    Log.w(TAG, "signInAnonymously:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }


            }
        // Function that takes messages and sends them to the FireBase database.


        binding.btnChatSend.setOnClickListener {
            val messageText = messageEditText.text.toString()
            if (messageText.isNotEmpty()) {
                val currentUser = FirebaseAuth.getInstance().currentUser
                val userId = currentUser?.uid ?: "anonymous"
                val message = ChatMessage(userId, messageText, System.currentTimeMillis())
                sendMessageToFirebase(message)
                messageEditText.text = null

            }
            else {
                // Log an error if the message text is empty
                Log.e(TAG, "Message text is empty")
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

    fun sendMessageToFirebase(message: ChatMessage) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("messages")
        databaseReference.push().setValue(message)
    }

    // Function to fetch messages from Firebase
    fun fetchMessagesFromFirebase() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("messages")
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

    private fun fetchMessagesFromBackEnd() {
        // Assuming you have a "chat" node in your Firebase Realtime Database
        val databaseReference = firebaseDatabase.getReference("messages")

        // Attach a ValueEventListener to fetch messages
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messagesFromFirebase: MutableList<ChatMessage> = mutableListOf()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(ChatMessage::class.java)
                    message?.let {
                        messagesFromFirebase.add(it)
                    }
                }
                // Update your RecyclerView adapter with the fetched messages
                adapter.setMessages(messagesFromFirebase)
                // Scroll to the last message
                recyclerView.scrollToPosition(adapter.itemCount - 1)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Log.e(TAG, "Failed to fetch messages: ${error.message}")
            }
        })
    }
}



