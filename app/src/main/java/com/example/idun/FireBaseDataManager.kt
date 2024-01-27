package com.example.idun

import com.google.firebase.database.FirebaseDatabase

class FireBaseDataManager:ChatDataManager {
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun saveMessage(message: String) {
        TODO("Not yet implemented")
    }

    override fun getMessages(): List<String> {
        TODO("Not yet implemented")
    }

    override fun sendMessage(message: ChatMessage) {
        // Push the message to Firebase database
        val databaseReference = firebaseDatabase.getReference("chat")
        databaseReference.push().setValue(message)
    }

}