package com.example.mymuzzapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val sender: String,
    val timestamp: Date,
    val isRead: Boolean,
)
