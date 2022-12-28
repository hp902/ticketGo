package com.example.ticketgo.ui.events

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ticketgo.R
import com.example.ticketgo.base.BaseFragment
import com.example.ticketgo.databinding.FragmentEventsBinding
import com.example.ticketgo.ui.event_type.EventType
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventsFragment : BaseFragment() {

    private lateinit var binding: FragmentEventsBinding

    private val viewModel by viewModel<EventViewModel>()

    private lateinit var adapter: EventsAdapter

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData(view: View) {

        adapter = EventsAdapter { event: Event ->
            onEventClicked(event.eventId)
        }

        binding.rcvEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvEvents.adapter = adapter

        binding.chipGroup.apply {
            layoutDirection = View.LAYOUT_DIRECTION_LOCALE
            isSingleSelection = true
            isSelectionRequired = true
        }

        viewModel.eventsCategory.observe(this) { onEventsCategoryData(it) }
        viewModel.eventsData.observe(this) { onEventsData(it) }
    }

    override fun initListener(view: View) {

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.getChildAt(checkedId) as Chip
            filterEventByCategory(chip.tag as String)
        }
    }

    private fun onEventsCategoryData(eventCategoryList: List<EventType>) {
        eventCategoryList.forEachIndexed { index, eventType ->
            binding.chipGroup.addView(createTagChip(eventType, index))
        }
    }

    private fun onEventsData(eventList: List<Event>) {
        adapter.submitList(eventList)
    }


    private fun createTagChip(eventType: EventType, index: Int): Chip {
        val chip = layoutInflater.inflate(
            R.layout.chip_item, binding.chipGroup, false
        ) as Chip
        chip.text = eventType.name
        chip.tag = eventType.eventType
        chip.isCheckedIconVisible = false
        chip.id = index
        if (index == 0) {
            chip.isChecked = true
        }
        chip.isClickable = true
        chip.isCheckable = true
        return chip
    }

    private fun filterEventByCategory(eventCategory: String) {
        viewModel.getEvents(eventCategory)
    }

    private fun onEventClicked(eventId: Int) {
        Log.d("EventsFragment", "onEventClicked: $eventId")
    }
}