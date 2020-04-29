package com.sch.playandroid.ui.splash

import android.os.Bundle
import com.sch.playandroid.R
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.ui.main.MainActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SplashActivity :
    BaseActivity(), SplashPresenterConstant.ISplashView {
    val persenterImpl by lazy {
        SplashPresenterImpl(
            this
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun init(savedInstanceState: Bundle?) {
        //请求权限
        persenterImpl.requestPermission(this)
    }

    /**
     * 权限请求成功
     */
    override fun onSuccess() {
        doAsync {
            Thread.sleep(1500)
            uiThread {
                intent(MainActivity::class.java, false)
                finish()
            }
        }
    }
    /**
     * 权限请求失败
     */
    override fun onFail() {
        doAsync {
            Thread.sleep(1500)
            uiThread {
                intent(MainActivity::class.java, false)
                finish()
            }
        }
    }

}