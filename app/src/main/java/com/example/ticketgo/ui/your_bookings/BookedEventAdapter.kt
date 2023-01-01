package com.example.ticketgo.ui.your_bookings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketgo.databinding.BookedEventItemBinding
import com.example.ticketgo.ui.ticket.BookedEvents
import com.example.ticketgo.utils.DateTimeUtils

class BookedEventAdapter : ListAdapter<BookedEvents, BookedEventAdapter.ViewHolder>(DiffUtilCall) {

    inner class ViewHolder(val binding: BookedEventItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            BookedEventItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        with(holder.binding) {
            tvEventName.text = data.eventName.trim()
            val time =
                "at:- " + DateTimeUtils.convertDateString(data.startTime, DateTimeUtils.TIME_DATE)
            tvEventTime.text = time
            val seats = "seats:- " + data.seats.joinToString {
                it.rowId + it.columnId
            }
            tvSeats.text = seats
        }
    }

    object DiffUtilCall : DiffUtil.ItemCallback<BookedEvents>() {
        override fun areItemsTheSame(oldItem: BookedEvents, newItem: BookedEvents): Boolean {
            return oldItem.eventId == newItem.eventId
        }

        override fun areContentsTheSame(oldItem: BookedEvents, newItem: BookedEvents): Boolean {
            return oldItem == newItem
        }
    }
}