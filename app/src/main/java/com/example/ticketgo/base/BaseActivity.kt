package com.example.ticketgo.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.ticketgo.utils.Constants
import com.example.ticketgo.utils.LoadingDialog

abstract class BaseActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private val mRunnable: Runnable = Runnable {
        (supportFragmentManager.findFragmentByTag(LoadingDialog::class.java.simpleName) as? LoadingDialog)?.dismissAllowingStateLoss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
        initListener()
        mDelayHandler = Handler(Looper.getMainLooper())
    }

    abstract fun initView()

    abstract fun initData()

    abstract fun initListener()

    fun showLoadingDialog(status: Boolean) {
        val fm = supportFragmentManager
        if (status) {
            LoadingDialog.newInstance().show(fm, LoadingDialog::class.java.simpleName)
        } else {
            if (mDelayHandler != null) {
                mDelayHandler?.postDelayed(mRunnable, Constants.DIALOG_DELAY)
            }
        }
    }
}



