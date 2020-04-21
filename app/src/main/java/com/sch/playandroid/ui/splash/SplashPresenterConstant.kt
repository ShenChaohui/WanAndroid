package com.sch.playandroid.ui.splash

import android.content.Context

class SplashPresenterConstant {

    interface ISplashPresenter {
        fun requestPermission(context: Context)
    }

    interface ISplashView {
        fun onSuccess()
        fun onFail()
    }
}