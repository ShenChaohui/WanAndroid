package com.sch.playandroid.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sch.playandroid.util.AppManager
import com.sch.playandroid.util.ColorUtils
import com.sch.playandroid.util.StatusUtils
import org.jetbrains.anko.toast

abstract class BaseActivity: AppCompatActivity(){
    protected val TAG = javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutId = getLayoutId()
        if (layoutId != 0) {
            setContentView(layoutId)
        }
        setStatusColor()
        setSystemInvadeBlack()

        init(savedInstanceState)
    }
    protected abstract fun init(savedInstanceState: Bundle?)
    protected abstract fun getLayoutId(): Int


    protected fun myToast(msg: String) {
        runOnUiThread { toast(msg) }
    }

    /**
     * 界面跳转
     * @param isLogin 启动界面是否需要登录
     */
    protected fun intent(clazz: Class<*>, isLogin: Boolean) {
        //需要登录&&未登录
        if (isLogin && !AppManager.isLogin()) {
//            startActivity(Intent(this, LoginActivity::class.java))
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
//            startActivity(Intent(this, LoginActivity::class.java))
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
}