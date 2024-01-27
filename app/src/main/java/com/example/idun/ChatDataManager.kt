package com.example.idun

import android.content.Context

interface ChatDataManager{
    fun saveMessage (message: String)
    fun getMessages(): List<String>
    fun sendMessage(message:ChatMessage)



}