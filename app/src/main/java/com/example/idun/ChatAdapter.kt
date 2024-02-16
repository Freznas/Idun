package com.example.idun

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(val context: ChatActivity) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    private val messages: MutableList<ChatMessage> = mutableListOf()

    // This function is connected to my XML layout "listview_chat" which handles the design aspect
    // of each individual element in the listview.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.listview_chat, parent,false)
        return ViewHolder(view)
    }

// This function gets the message,and places it in the array by updating the views with the bind() method.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messages[position])

    }
// This functions return the amount of messages from the messages list
// (so "hi","how are you" and " good thanks". would return 3 because its 3 messages)
    override fun getItemCount(): Int {
        return messages.size
    }
//this function adds the message written and adds it to the list
    @SuppressLint("NotifyDataSetChanged")
    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyDataSetChanged()
    }

    //Clears the list in order to reload it and to place the list in whole in the correct order.
    @SuppressLint("NotifyDataSetChanged")
    fun setMessages(newMessages: List<ChatMessage>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Declare TextView properties here
        private val messageTextView: TextView = itemView.findViewById(R.id.tv_PlaceholderChatTitle)
        private val senderTextView: TextView = itemView.findViewById(R.id.tv_PlaceholderChatBread)

        fun bind(message: ChatMessage) {
//hämtar från ChatMessage klassen
            messageTextView.text = message.content
            senderTextView.text = message.sender
        }
    }

}
