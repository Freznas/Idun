package com.example.idun

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

data class ChatMessage(
    var sender: String = "",
    var content: String = "",
    var timestamp: Long = 0
) {
    // No-argument constructor required by Firebase
    constructor() : this("", "", 0)
}



