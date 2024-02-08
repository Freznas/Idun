package com.example.idun

import android.content.Context
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

interface FetchMessagesCallback {
    fun onMessagesFetched(messages: List<ChatMessage>)
}

class FireBaseDataManager(private val context: Context) : ChatDataManager {

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun saveMessage(message: String) {
        val databaseReference = firebaseDatabase.getReference("messages")
        databaseReference.push().setValue(message)
    }

    override suspend fun getMessages(): List<ChatMessage> = withContext(Dispatchers.IO) {
        val messages: MutableList<ChatMessage> = mutableListOf()
        val databaseReference = firebaseDatabase.getReference("chat")
        val snapshot = databaseReference.get().await()
      for (childSnapshot in snapshot.children) {
          val message = childSnapshot.getValue(ChatMessage::class.java)
          message?.let {
              messages.add(it)
          }
      }
        messages
}

override fun sendMessage(message: ChatMessage) {
    // Push the message to Firebase database
    val databaseReference = firebaseDatabase.getReference("chat")
    databaseReference.push().setValue(message)
}

}