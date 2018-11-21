package com.assistant.ua.business.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.assistant.ua.R
import com.assistant.ua.bridge.SplashPresenter
import com.assistant.ua.databinding.ActivitySplashBinding
import com.assistant.ua.framework.base.ui.BaseActivity
import com.assistant.ua.framework.permission.Permission
import com.assistant.ua.framework.permission.PermissionManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/** Created by qinbaoyuan on 2018/11/10
 */
class SplashActivity : BaseActivity() {
    private lateinit var splashBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        splashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        lifecycle.addObserver(SplashPresenter())
        splashBinding.txtSplashNum.setOnClickListener { startMainActivity() }

        if (PermissionManager.getInstance().hasPermission(this, Permission.getBasePermission())) {
            showCountDown()
            val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            Log.e("andyqin", "phoneNum = ${telephonyManager.line1Number}")
        } else {
            applyBasePermission()
        }
    }

    /**
     * 授权成功等回调方法
     */
    override fun onBasePermissionGranted() {
        showCountDown()
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        Log.e("andyqin", "phoneNum = ${telephonyManager.line1Number}")
    }

    @SuppressLint("CheckResult")
    private fun showCountDown() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
            .take(2)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                if ((it - 1L) == 0L) {
                    startMainActivity()
                }
            }
    }

    private fun startMainActivity() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}