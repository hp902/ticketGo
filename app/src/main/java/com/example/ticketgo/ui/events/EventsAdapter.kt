package com.example.ticketgo.ui.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ticketgo.R
import com.example.ticketgo.databinding.EventItemBinding
import com.example.ticketgo.utils.DateTimeUtils

class EventsAdapter(
    private val onItemClicked: (Event) -> Unit
) : ListAdapter<Event, EventsAdapter.ViewHolder>(DiffUtilCall) {

    inner class ViewHolder(val binding: EventItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            EventItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)

        with(holder.binding) {

            if (data.bannerImageUrl.isNotEmpty()) {
                Glide.with(ivEventImg.context).load(data.bannerImageUrl)
                    .into(ivEventImg)
            }
            tvDescription.text = data.eventDescription
            tvEventName.text = data.eventName
            tvStartTime.text = tvStartTime.context.getString(
                R.string.start_time,
                DateTimeUtils.convertDate(data.startTime)
            )
            tvDuration.text = tvDuration.context.getString(
                R.string.duration,
                DateTimeUtils.convertSecToTime(second = data.eventDurationSecond)
            )
        }
        holder.itemView.setOnClickListener {
            onItemClicked(data)
        }
    }

    object DiffUtilCall : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.eventId == newItem.eventId
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }

    }
}