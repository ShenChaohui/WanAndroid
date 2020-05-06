package com.sch.playandroid.ui.splash

import android.content.Context
import com.sch.playandroid.base.IBasePresenter
import com.sch.playandroid.base.IBaseView

class SplashConstant {

    interface IPresenter:IBasePresenter {
        fun requestPermission(context: Context)
    }

    interface IView :IBaseView{
        fun onSuccess()
    }
}