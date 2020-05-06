package com.sch.playandroid.ui.splash

import android.os.Bundle
import com.sch.playandroid.R
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.ui.main.MainActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SplashActivity :
    BaseActivity<SplashConstant.IPresenter>(), SplashConstant.IView {

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun init(savedInstanceState: Bundle?) {
        //请求权限
        mPresenter?.requestPermission(this)
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
    override fun onError(ex: String) {
        doAsync {
            Thread.sleep(1500)
            uiThread {
                intent(MainActivity::class.java, false)
                finish()
            }
        }
    }
    override fun createPresenter(): SplashConstant.IPresenter? {
        return SplashPresenterImpl()
    }

}