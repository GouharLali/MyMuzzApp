package com.example.mymuzzapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var chatMessageInput: EditText
    private lateinit var messageSendBtn: ImageButton
    private lateinit var dataMessageList: MutableList<DataMessage>
    private lateinit var messageAdapter: MessageAdapter

    private val otherUserResponses = arrayOf(
        "Hello!",
        "How are you?",
        "Nice to meet you!",
        "What's up?",
        "I'm here!",
        "Tell me more."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_list_screen)

        // Initialize views
        messageRecyclerView = findViewById(R.id.messageRecyclerView)
        chatMessageInput = findViewById(R.id.chat_message_input)
        messageSendBtn = findViewById(R.id.message_send_btn)

        // Initialize message list and adapter
        dataMessageList = mutableListOf()
        messageAdapter = MessageAdapter(dataMessageList, this)
        messageRecyclerView.adapter = messageAdapter
        messageRecyclerView.layoutManager = LinearLayoutManager(this)

        // Set default hint for chat message input
        chatMessageInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                chatMessageInput.hint = ""
                messageSendBtn.setBackgroundResource(R.drawable.focus_background)
                chatMessageInput.setBackgroundResource(R.drawable.edit_text_red_outline)
            } else {
                messageSendBtn.setBackgroundResource(R.drawable.default_sent_background)
                chatMessageInput.setBackgroundResource(R.drawable.default_background)
            }
        }

        // Handle send button click
        messageSendBtn.setOnClickListener {
            sendMessage()
            triggerOtherUserMessage()
        }
    }

    private fun sendMessage() {
        val messageText = chatMessageInput.text.toString().trim()
        if (messageText.isNotEmpty()) {
            val currentTime = Calendar.getInstance().time
            val dayTimestamp = calculateDayTimestamp(currentTime)
            val dataMessage = DataMessage(messageText, "sender", currentTime, dayTimestamp, false)
            dataMessageList.add(dataMessage)
            messageAdapter.notifyDataSetChanged()
            dataMessage.isRead = true
            chatMessageInput.text.clear()
            messageRecyclerView.scrollToPosition(dataMessageList.size - 1)
        }
    }

    private fun triggerOtherUserMessage() {
        val randomIndex = Random().nextInt(otherUserResponses.size)
        val randomResponse = otherUserResponses[randomIndex]

        val currentTime = Calendar.getInstance().time
        val dayTimestamp = calculateDayTimestamp(currentTime)
        val otherUserDataMessage = DataMessage(randomResponse, "other", currentTime, dayTimestamp, true)
        dataMessageList.add(otherUserDataMessage)
        messageAdapter.notifyDataSetChanged()
        messageRecyclerView.scrollToPosition(dataMessageList.size - 1)
    }

    private fun calculateDayTimestamp(timestamp: Date): Long {
        val lastMessageDayTimestamp = dataMessageList.lastOrNull()?.dayTimestamp
        return if (lastMessageDayTimestamp == null ||
            (timestamp.time / (1000 * 60 * 60 * 24)) != lastMessageDayTimestamp
        ) {
            timestamp.time / (1000 * 60 * 60 * 24)
        } else {
            lastMessageDayTimestamp
        }
    }
}
