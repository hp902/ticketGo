package com.example.ticketgo.ui.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ticketgo.base.BaseFragment
import com.example.ticketgo.databinding.FragmentAccountBinding

class AccountFragment : BaseFragment() {

    private lateinit var binding: FragmentAccountBinding

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData(view: View) {

    }

    override fun initListener(view: View) {

    }
}