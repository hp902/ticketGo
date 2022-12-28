package com.example.ticketgo.ui.ticket

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ticketgo.R
import com.example.ticketgo.base.BaseFragment
import com.example.ticketgo.databinding.FragmentTicketBookingBinding
import com.example.ticketgo.ui.events.Event
import com.example.ticketgo.ui.events.EventViewModel
import com.example.ticketgo.ui.events.Seat
import com.example.ticketgo.utils.DateTimeUtils
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class TicketBookingFragment : BaseFragment(), SeatAdapter.OnClickListener {

    companion object {
        private const val TAG: String = "TicketBookingFragment"
    }

    private lateinit var binding: FragmentTicketBookingBinding

    private val eventsViewModel by viewModel<EventViewModel>()

    private var eventId by Delegates.notNull<Int>()
    private var eventList = MutableLiveData<ArrayList<Event>>()

    private lateinit var seatAdapter: SeatAdapter


    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentTicketBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData(view: View) {
        arguments?.let {
            eventId = TicketBookingFragmentArgs.fromBundle(it).eventId
        } ?: run {
            findNavController().navigateUp()
        }

        binding.includeHeader.tvHeading.text = getString(R.string.book_seats)

        seatAdapter = SeatAdapter(this)

        binding.rcvSeats.apply {
            layoutManager = GridLayoutManager(requireContext(), 15)
            adapter = seatAdapter
        }

        binding.chipGroup.apply {
            layoutDirection = View.LAYOUT_DIRECTION_LOCALE
            isSingleSelection = true
            isSelectionRequired = true
        }

        eventsViewModel.getEvents()
        eventsViewModel.eventsData.observe(viewLifecycleOwner) { onEventList(it as ArrayList<Event>) }
    }

    override fun initListener(view: View) {
        binding.includeHeader.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.chipGroup.setOnCheckedChangeListener { _, _ ->
            // Test
        }
    }

    private fun onEventList(eventList: ArrayList<Event>) {
        if (this.eventList.value == null || this.eventList.value?.isEmpty() == true) {
            this.eventList.value = eventList
            setUpEventChips()
            Log.d(TAG, "onEventList: ")
        }
    }

    private fun setUpEventChips() {
        eventList.value!!.forEachIndexed { index, event ->
            binding.chipGroup.addView(createTagChip(event, index))
        }
    }


    private fun createTagChip(event: Event, index: Int): Chip {
        val chip = layoutInflater.inflate(
            R.layout.chip_item, binding.chipGroup, false
        ) as Chip
        val chipText = "${event.eventName}: ${
            DateTimeUtils.convertDateString(
                event.startTime,
                DateTimeUtils.TIME
            )
        }"
        chip.text = chipText
        chip.isCheckedIconVisible = false
        chip.id = index
        if (eventId == event.eventId) {
            chip.isChecked = true
            onEventChecked(eventId)
        }
        chip.isClickable = true
        chip.isCheckable = true
        chip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onEventChecked(event.eventId)
            }
        }
        return chip
    }

    private fun onEventChecked(eventId: Int) {
        this.eventId = eventId
        seatAdapter.submitList(eventList.value?.get(eventId - 1)?.seats)
    }

    override fun onItemClicked(seat: Seat, position: Int) {
        eventList.value?.get(eventId - 1)?.seats?.get(position).apply {
            this?.status = seat.status
        }
    }
}