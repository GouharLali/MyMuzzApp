package com.example.mymuzzapp

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepository(private val messageDao: MessageDao) {
    val allMessages: LiveData<List<MessageEntity>> = messageDao.getAllMessages()

    suspend fun insert(message: MessageEntity) {
        withContext(Dispatchers.IO) {
            messageDao.insertMessage(message)
        }
    }
}
