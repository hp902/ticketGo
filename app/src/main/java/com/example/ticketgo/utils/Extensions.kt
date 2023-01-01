package com.example.ticketgo.utils

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * show toast in fragments
 * @param message this contains the string resource
 * @param isLong set value is true if long toast is needed
 */
fun Fragment.toast(message: String, isLong: Boolean = false) {
    try {
        Toast.makeText(
            requireContext(),
            message,
            if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        ).show()
    } catch (exception: Exception) {
        Log.e("Toast", "Failed to show toast ", exception)
    }
}