package com.example.ticketgo

import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.ticketgo.base.BaseActivity
import com.example.ticketgo.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    companion object {
        private const val TAG: String = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    override fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initData() {

        //binding.bottomNavigationView.setupWithNavController(binding.navHostFragment.findNavController())

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navigationController = navHostFragment.navController
        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navigationController
        )

        navigationController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentEvents -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.fragmentsYourBookings -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.fragmentAccount -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
                else -> binding.bottomNavigationView.visibility = View.GONE
            }
        }
    }

    override fun initListener() {

    }
}