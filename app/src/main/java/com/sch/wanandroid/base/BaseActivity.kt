package com.sch.wanandroid.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sch.wanandroid.constants.Constants
import com.sch.wanandroid.ui.login.LoginActivity
import com.sch.wanandroid.util.AppManager
import com.sch.wanandroid.util.ColorUtils
import com.sch.wanandroid.util.PrefUtils
import com.sch.wanandroid.util.StatusUtils

abstract class BaseActivity<P : IBasePresenter> : AppCompatActivity(), IBaseView {
    protected val TAG = javaClass.name
    protected var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (PrefUtils.getBoolean(Constants.NIGHT_MODEL, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        mPresenter = createPresenter()
        mPresenter?.let {
            mPresenter?.attachView(this)
        }
        val layoutId = getLayoutId()
        if (layoutId != 0) {
            setContentView(layoutId)
        }
        setStatusColor()
        setSystemInvadeBlack()

        init(savedInstanceState)
    }

    protected abstract fun createPresenter(): P?
    protected abstract fun getLayoutId(): Int
    protected abstract fun init(savedInstanceState: Bundle?)

    /**
     * 界面跳转
     * @param isLogin 启动界面是否需要登录
     */
    protected fun intent(clazz: Class<*>, isLogin: Boolean) {
        //需要登录&&未登录
        if (isLogin && !AppManager.isLogin()) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, clazz))
        }
    }

    /**
     * 携带bundle跳转
     * @param isLogin 启动界面是否需要登录
     */
    protected fun intent(bundle: Bundle, clazz: Class<*>, isLogin: Boolean) {
        //需要登录&&未登录
        if (isLogin && !AppManager.isLogin()) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, clazz).apply {
                putExtras(bundle)
            })
        }
    }

    /**
     * 设置状态栏背景颜色
     */
    protected open fun setStatusColor() {
        StatusUtils.setUseStatusBarColor(this, ColorUtils.parseColor("#00ffffff"))
    }

    /**
     * 沉浸式状态
     */
    protected open fun setSystemInvadeBlack() {
        //第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtils.setSystemStatus(this, true, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }
}