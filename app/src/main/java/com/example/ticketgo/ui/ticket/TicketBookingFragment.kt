package com.example.ticketgo.ui.ticket

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ticketgo.R
import com.example.ticketgo.base.BaseActivity
import com.example.ticketgo.base.BaseFragment
import com.example.ticketgo.databinding.FragmentTicketBookingBinding
import com.example.ticketgo.ui.events.Event
import com.example.ticketgo.ui.events.EventViewModel
import com.example.ticketgo.ui.events.Seat
import com.example.ticketgo.utils.DateTimeUtils
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketBookingFragment : BaseFragment(), SeatAdapter.OnClickListener {

    private lateinit var binding: FragmentTicketBookingBinding

    private val eventsViewModel by viewModel<EventViewModel>()
    private val viewModel by viewModel<TicketBookingViewModel>()

    private var eventDate: String = ""

    private lateinit var seatAdapter: SeatAdapter


    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentTicketBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData(view: View) {
        arguments?.let {
            viewModel.eventId = TicketBookingFragmentArgs.fromBundle(it).eventId
            eventDate = TicketBookingFragmentArgs.fromBundle(it).date
        } ?: run {
            findNavController().navigateUp()
        }

        binding.includeHeader.tvHeading.text = getString(R.string.book_seats)
        binding.tvDate.text = eventDate

        seatAdapter = SeatAdapter(this)

        binding.rcvSeats.apply {
            layoutManager = GridLayoutManager(requireContext(), 15)
            adapter = seatAdapter
            hasFixedSize()
        }

        binding.chipGroup.apply {
            layoutDirection = View.LAYOUT_DIRECTION_LOCALE
            isSingleSelection = true
            isSelectionRequired = true
        }

        eventsViewModel.getEvents()
        eventsViewModel.eventsData.observe(viewLifecycleOwner) { onEventList(it as ArrayList<Event>) }

        viewModel.selectedSeats.observe(viewLifecycleOwner) {
            binding.tvSeats.text = getString(R.string.selected_seats, it ?: 0)
            binding.btBookTicket.isEnabled = it > 0
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it == true) {
                (requireActivity() as BaseActivity).showLoadingDialog(true)
            } else {
                (requireActivity() as BaseActivity).showLoadingDialog(false)
            }
        }
    }

    override fun initListener(view: View) {
        binding.includeHeader.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.chipGroup.setOnCheckedChangeListener { _, _ ->
            // Test
        }

        binding.btBookTicket.setOnClickListener {
            viewModel.bookTicket()
        }
    }

    private fun onEventList(eventList: ArrayList<Event>) {
        if (viewModel.eventList.isEmpty()) {
            viewModel.eventList.addAll(eventList)
            setUpEventChips()
        }
    }

    private fun setUpEventChips() {
        viewModel.eventList.forEachIndexed { index, event ->
            binding.chipGroup.addView(createTagChip(event, index))
        }
    }


    private fun createTagChip(event: Event, index: Int): Chip {
        val chip = layoutInflater.inflate(
            R.layout.chip_item, binding.chipGroup, false
        ) as Chip
        val chipText = "${event.eventName}: ${
            DateTimeUtils.convertDateString(
                event.startTime, DateTimeUtils.TIME
            )
        }"
        chip.text = chipText
        chip.isCheckedIconVisible = false
        chip.id = index
        if (viewModel.eventId == event.eventId) {
            chip.isChecked = true
            onEventChecked(viewModel.eventId)
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
        viewModel.eventId = eventId
        seatAdapter.submitList(viewModel.eventList[eventId - 1].seats)
    }

    override fun onItemClicked(seat: Seat, position: Int, status: Int) {
        /*lifecycleScope.launch {
            eventList.value?.get(eventId - 1)?.seats?.get(position).apply {
                this?.status = seat.status
            }
            selectedSeats.postValue(selectedSeats.value?.plus(status) ?: status)
            val eventSeats = selectedEvents[eventId]
            if (status > 0) {
                selectedEvents[eventId] = status.plus(eventSeats ?: 0)
            } else {
                if (eventSeats == 1 || eventSeats == 0 || eventSeats == null) {
                    selectedEvents.remove(eventId)
                } else {
                    selectedEvents[eventId] = status.plus(eventSeats)
                }
            }
        }*/
        viewModel.onSeatSelected(seat, position, status)
    }
}