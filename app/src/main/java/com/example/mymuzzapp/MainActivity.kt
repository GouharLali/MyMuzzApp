package com.example.mymuzzapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var chatMessageInput: EditText
    private lateinit var messageSendBtn: ImageButton
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageViewModel: MessageViewModel

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

        // Initialize ViewModel
        messageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)

        // Initialize views
        messageRecyclerView = findViewById(R.id.messageRecyclerView)
        chatMessageInput = findViewById(R.id.chat_message_input)
        messageSendBtn = findViewById(R.id.message_send_btn)

        // Initialize message adapter
        messageAdapter = MessageAdapter(this)
        messageRecyclerView.adapter = messageAdapter
        messageRecyclerView.layoutManager = LinearLayoutManager(this)

        // Observe LiveData in ViewModel
        messageViewModel.allMessages.observe(this, Observer { messages ->
            messages?.let {
                messageAdapter.setData(messages)
                messageRecyclerView.scrollToPosition(messages.size - 1)
            }
        })

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
            val lastMessageTimestamp = messageViewModel.allMessages.value?.lastOrNull()?.timestamp
            calculateDayTimestamp(currentTime, lastMessageTimestamp)
            val dataMessage = MessageEntity(text = messageText, sender = "sender", timestamp = currentTime, isRead = true)
            messageViewModel.insert(dataMessage)
            chatMessageInput.text.clear()
        }
    }

    private fun triggerOtherUserMessage() {
        val randomIndex = (0 until otherUserResponses.size).random()
        val randomResponse = otherUserResponses[randomIndex]

        val currentTime = Calendar.getInstance().time
        val otherUserDataMessage = MessageEntity(text = randomResponse, sender = "other", timestamp = currentTime, isRead = true)
        messageViewModel.insert(otherUserDataMessage)
    }

    private fun calculateDayTimestamp(timestamp: Date, lastMessageTimestamp: Date?): Long {
        val calendar = Calendar.getInstance()
        calendar.time = timestamp
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val currentDayTimestamp = calendar.timeInMillis

        val lastMessageDayTimestamp = lastMessageTimestamp?.let { calculateDayTimestamp(it, null) }

        return if (lastMessageDayTimestamp == null || currentDayTimestamp != lastMessageDayTimestamp) {
            currentDayTimestamp
        } else {
            lastMessageDayTimestamp
        }
    }

}
