package com.example.idun

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

data class ChatMessage( val sender: String,
                        val content: String,
                        val timestamp:Long = System.currentTimeMillis())




