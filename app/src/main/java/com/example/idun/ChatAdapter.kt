package com.example.idun

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(val context: ChatActivity) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    private val messages: MutableList<ChatMessage> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.listview_chat, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messages[position])

    }

    override fun getItemCount(): Int {
        return messages.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyDataSetChanged()
    }
    fun setMessages(newMessages: List<ChatMessage>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Declare TextView properties here
        private val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
        private val senderTextView: TextView = itemView.findViewById(R.id.senderTextView)

        fun bind(message: ChatMessage) {
//hämtar från ChatMessage klassen
            messageTextView.text = message.content
            senderTextView.text = message.sender
        }
    }


    companion object {
        val itemCount: Any
            get() {
                TODO()
            }


    }


}
