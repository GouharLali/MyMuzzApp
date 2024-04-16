package com.example.mymuzzapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MessageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MessageRepository
    val allMessages: LiveData<List<MessageEntity>>

    init {
        val messageDao = MessageDatabase.getDatabase(application).messageDao()
        repository = MessageRepository(messageDao)
        allMessages = repository.allMessages
    }

    fun insert(message: MessageEntity) = viewModelScope.launch {
        repository.insert(message)
    }
}

