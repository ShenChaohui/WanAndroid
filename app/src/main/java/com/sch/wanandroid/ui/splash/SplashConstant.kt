package com.sch.wanandroid.ui.splash

import android.content.Context
import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView

class SplashConstant {

    interface IPresenter:IBasePresenter {
        fun requestPermission(context: Context)
    }

    interface IView :IBaseView{
        fun onSuccess()
    }
}