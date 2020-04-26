package com.sch.lolcosmos.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sch.playandroid.ui.login.LoginActivity
import com.sch.playandroid.util.AppManager

abstract class BaseFragment : Fragment() {
    protected var TAG = javaClass.name
    var contentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contentView = inflater.inflate(getLayoutId(), null)
        return contentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init(savedInstanceState)
    }


    /**
     * 界面跳转
     * @param isLogin 启动界面是否需要登录
     */
    protected fun intent(clazz: Class<*>, isLogin: Boolean) {
        //需要登录&&未登录
        if (isLogin && !AppManager.isLogin()) {
            startActivity(Intent(context, LoginActivity::class.java))
        } else {
            startActivity(Intent(context, clazz))
        }
    }

    /**
     * 携带bundle跳转
     * @param isLogin 启动界面是否需要登录
     */
    protected fun intent(bundle: Bundle, clazz: Class<*>, isLogin: Boolean) {
        //需要登录&&未登录
        if (isLogin && !AppManager.isLogin()) {
            startActivity(Intent(context, LoginActivity::class.java))
        } else {
            startActivity(Intent(context, clazz).apply {
                putExtras(bundle)
            })
        }
    }

    protected abstract fun init(savedInstanceState: Bundle?)
    protected abstract fun getLayoutId(): Int
}