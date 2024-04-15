package com.example.mymuzzapp

import java.util.Date

data class DataMessage(
    val text: String,
    val sender: String,
    val timestamp: Date,
    val dayTimestamp: Long? = null,
    var isRead: Boolean
)
