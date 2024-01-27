package com.example.idun

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(val context: ChatActivity) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    private val messages: MutableList<ChatMessage> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    fun onBindViewHolder(holder: ChatAdapter, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    companion object {
        val itemCount: Any
            get() {
                TODO()
            }

        fun addMessage(message: ChatMessage) {
            TODO("Not yet implemented")
        }
    }
}
