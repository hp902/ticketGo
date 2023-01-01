package com.example.ticketgo.ui.your_bookings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ticketgo.base.BaseFragment
import com.example.ticketgo.databinding.FragmentYourBookingsBinding
import com.example.ticketgo.ui.ticket.Ticket
import com.example.ticketgo.utils.FileUtil
import com.example.ticketgo.utils.toast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class YourBookingsFragment : BaseFragment(), YourBookingsAdapter.ClickListener {

    private lateinit var binding: FragmentYourBookingsBinding
    private lateinit var adapter: YourBookingsAdapter

    private val viewModel by viewModel<YourBookingsViewModel>()

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentYourBookingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData(view: View) {
        adapter = YourBookingsAdapter(this)

        binding.rcvYourBooking.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvYourBooking.adapter = adapter

        viewModel.getTickets()
        viewModel.ticketData.observe(viewLifecycleOwner) { onTicketData(it) }
    }

    override fun initListener(view: View) {

    }

    private fun onTicketData(it: List<Ticket>) {
        adapter.submitList(it)
    }

    override fun onCancelClicked(ticket: Ticket) {
        viewModel.cancelTicket(ticket)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onDownloadClicked(view: View, fileName: String) {
        GlobalScope.launch {
            FileUtil.createPdf(view, requireActivity(), fileName,requireContext())
            toast("Ticket Downloaded successfully")
        }
    }
}