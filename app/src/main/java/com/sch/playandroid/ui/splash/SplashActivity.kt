package com.sch.playandroid.ui.splash

import android.os.Bundle
import com.sch.playandroid.R
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.ui.main.MainActivity

class SplashActivity :
    BaseActivity(), SplashPresenterConstant.ISplashView {
    val persenter by lazy {
        SplashPresenterImpl(
            this
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun init(savedInstanceState: Bundle?) {
        persenter.requestPermission(this)
    }


    override fun onSuccess() {
        Thread(Runnable {
            Thread.sleep(2000)
            intent(MainActivity::class.java, false)
        }).start()
    }

    override fun onFail() {

    }

}