package com.example.mymuzzapp

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MessageAdapter(
    private val dataMessages: List<DataMessage>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_MESSAGE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.chat_header_item_layout, parent, false)
                HeaderViewHolder(view)
            }
            VIEW_TYPE_MESSAGE -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.message_item, parent, false)
                MessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                // Bind data for header view if needed
            }
            is MessageViewHolder -> {
                val message = dataMessages[position - 1] // Subtract 1 to adjust for header
                holder.bind(message)

                if (position > 1) {
                    val previousMessage = dataMessages[position - 2]
                    if (shouldShowDateDelayed(previousMessage, message)) {
                        holder.showDate(message.timestamp)
                    } else {
                        holder.hideDate()
                    }
                } else {
                    holder.hideDate()
                }
            }
        }
    }

    private fun shouldShowDateDelayed(
        previousDataMessage: DataMessage,
        currentDataMessage: DataMessage
    ): Boolean {
        val difference = currentDataMessage.timestamp.time - previousDataMessage.timestamp.time
        val hoursDifference = difference / 3600000
        return hoursDifference >= 1 || currentDataMessage.dayTimestamp != previousDataMessage.dayTimestamp
    }

    override fun getItemCount(): Int {
        return dataMessages.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_MESSAGE
        }
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize views for header view if needed
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val leftChatLayout: LinearLayout = itemView.findViewById(R.id.left_chat_layout)
        private val rightChatLayout: LinearLayout = itemView.findViewById(R.id.right_chat_layout)
        private val leftChatTextView: TextView = itemView.findViewById(R.id.left_chat_textview)
        private val rightChatTextView: TextView = itemView.findViewById(R.id.right_chat_textview)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val tickImageView: ImageView = itemView.findViewById(R.id.tickImageView)

        fun bind(dataMessage: DataMessage) {
            leftChatLayout.visibility = View.GONE
            rightChatLayout.visibility = View.GONE

            if (dataMessage.sender == "sender") {
                leftChatLayout.visibility = View.VISIBLE
                leftChatTextView.text = dataMessage.text
                tickImageView.visibility = if (dataMessage.isRead) View.VISIBLE else View.GONE
                tickImageView.setImageResource(if (dataMessage.isRead) R.drawable.tick_gold else R.drawable.tick_grey)
            } else {
                rightChatLayout.visibility = View.VISIBLE
                rightChatTextView.text = dataMessage.text
                tickImageView.visibility = View.GONE
            }
        }

        fun showDate(timestamp: Date) {
            dateTextView.visibility = View.VISIBLE
            val dateFormat = SimpleDateFormat("EEEE HH:mm", Locale.getDefault())
            val formattedDate = dateFormat.format(timestamp)
            val dateAndTime = formattedDate.split(" ")
            val date = dateAndTime[0]
            val time = dateAndTime[1]
            val spannableDate = SpannableString(formattedDate)
            spannableDate.setSpan(StyleSpan(Typeface.BOLD), 0, date.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            dateTextView.text = spannableDate
        }

        fun hideDate() {
            dateTextView.visibility = View.GONE
        }
    }
}
