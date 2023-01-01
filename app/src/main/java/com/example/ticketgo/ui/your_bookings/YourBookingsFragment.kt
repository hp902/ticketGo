package com.example.ticketgo.ui.your_bookings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ticketgo.base.BaseFragment
import com.example.ticketgo.databinding.FragmentYourBookingsBinding
import com.example.ticketgo.ui.ticket.Ticket
import org.koin.androidx.viewmodel.ext.android.viewModel

class YourBookingsFragment : BaseFragment() {

    private lateinit var binding: FragmentYourBookingsBinding
    private lateinit var adapter: YourBookingsAdapter

    private val viewModel by viewModel<YourBookingsViewModel>()

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentYourBookingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData(view: View) {
        adapter = YourBookingsAdapter {
            onTicketCanceledClicked(it)
        }

        binding.rcvYourBooking.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvYourBooking.adapter = adapter

        viewModel.getTickets()
        viewModel.tickerData.observe(viewLifecycleOwner) { onTicketData(it) }
    }

    override fun initListener(view: View) {

    }

    private fun onTicketData(it: List<Ticket>) {
        adapter.submitList(it)
    }

    private fun onTicketCanceledClicked(it: Ticket) {

    }
}