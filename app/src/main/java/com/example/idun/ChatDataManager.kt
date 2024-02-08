package com.example.idun

import android.content.Context

interface ChatDataManager{
    fun saveMessage (message: String)
    suspend fun getMessages(): List<ChatMessage>
    fun sendMessage(message:ChatMessage)



}