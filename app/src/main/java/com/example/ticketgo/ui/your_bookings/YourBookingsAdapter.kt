package com.example.ticketgo.ui.your_bookings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketgo.R
import com.example.ticketgo.databinding.TicketItemBinding
import com.example.ticketgo.ui.ticket.Ticket
import com.example.ticketgo.utils.Constants
import com.example.ticketgo.utils.DateTimeUtils

class YourBookingsAdapter(
    private val listener: ClickListener
) : ListAdapter<Ticket, YourBookingsAdapter.ViewHolder>(DiffUtilCall) {

    interface ClickListener {
        fun onCancelClicked(ticket: Ticket)
        fun onDownloadClicked(view: View, fileName: String)
    }

    inner class ViewHolder(val binding: TicketItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TicketItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        with(holder.binding) {
            tvTicketNumber.apply {
                text = resources.getString(R.string.ticket_number, data.ticketId)
            }
            val adapterEvent = BookedEventAdapter()
            adapterEvent.submitList(data.events)
            rcvBookedEvents.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = adapterEvent
            }
            val booked = "Booked at: " +
                    DateTimeUtils.convertDateString(data.createdAt, DateTimeUtils.TIME_DATE)
            tvBookedDate.text = booked

            when (data.ticketStatus) {
                Constants.TICKET_CANCELED -> {
                    btCancelTicket.isEnabled = false
                    btCancelTicket.text = "Canceled"
                }
                Constants.TICKET_BOOKED -> {
                    btCancelTicket.isEnabled = true
                    btCancelTicket.text = "Cancel Ticket"
                }
            }
            btCancelTicket.setOnClickListener {
                listener.onCancelClicked(data)
            }

            btDownload.setOnClickListener {
                val fileName = "Ticket_${data.ticketId}_${DateTimeUtils.getCurrentTimeForFile()}"
                listener.onDownloadClicked(root, fileName)
            }
        }
    }


    object DiffUtilCall : DiffUtil.ItemCallback<Ticket>() {
        override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem.ticketId == newItem.ticketId
        }

        override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem == newItem
        }
    }
}