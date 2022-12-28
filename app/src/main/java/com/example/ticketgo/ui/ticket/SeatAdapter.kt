package com.example.ticketgo.ui.ticket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketgo.R
import com.example.ticketgo.databinding.SeatItemBinding
import com.example.ticketgo.ui.events.Seat
import com.example.ticketgo.utils.Constants

class SeatAdapter(private val listener: OnClickListener) :
    ListAdapter<Seat, SeatAdapter.ViewHolder>(DiffUtilCall()) {

    interface OnClickListener {
        fun onItemClicked(seat: Seat, position: Int)
    }

    inner class ViewHolder(val binding: SeatItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SeatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        with(holder.binding) {
            when (data.status) {
                Constants.SEAT_BOOKED -> {
                    cvSeat.apply {
                        isEnabled = false
                        setCardBackgroundColor(resources.getColor(R.color.red_light, null))
                    }
                    tvSeatNumber.apply {
                        setTextColor(resources.getColor(R.color.white, null))
                    }
                }
                Constants.SEAT_SELECTED -> {
                    cvSeat.apply {
                        setCardBackgroundColor(resources.getColor(R.color.red, null))
                    }
                    tvSeatNumber.apply {
                        setTextColor(resources.getColor(R.color.white, null))
                    }
                }
                Constants.SEAT_EMPTY -> {
                    cvSeat.apply {
                        setCardBackgroundColor(resources.getColor(R.color.red_seat, null))
                    }
                    tvSeatNumber.apply {
                        setTextColor(resources.getColor(R.color.black, null))
                    }
                }
            }

            val seat = "${data.rowId} ${data.columnId}"
            tvSeatNumber.text = seat
            cvSeat.setOnClickListener {
                if (data.status == Constants.SEAT_EMPTY) {
                    listener.onItemClicked(data.apply {
                        status = Constants.SEAT_SELECTED
                    }, position)
                    notifyItemChanged(position)
                } else if (data.status == Constants.SEAT_SELECTED) {
                    listener.onItemClicked(data.apply {
                        status = Constants.SEAT_EMPTY
                    }, position)
                    notifyItemChanged(position)
                }
            }
        }
    }

    class DiffUtilCall : DiffUtil.ItemCallback<Seat>() {
        override fun areItemsTheSame(oldItem: Seat, newItem: Seat): Boolean {
            return oldItem.status == newItem.status
        }

        override fun areContentsTheSame(oldItem: Seat, newItem: Seat): Boolean {
            return oldItem == newItem
        }

    }
}