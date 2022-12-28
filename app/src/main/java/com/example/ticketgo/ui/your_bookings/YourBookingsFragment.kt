package com.example.ticketgo.ui.your_bookings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ticketgo.base.BaseFragment
import com.example.ticketgo.databinding.FragmentYourBookingsBinding

class YourBookingsFragment : BaseFragment() {

    private lateinit var binding: FragmentYourBookingsBinding

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentYourBookingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData(view: View) {

    }

    override fun initListener(view: View) {

    }
}