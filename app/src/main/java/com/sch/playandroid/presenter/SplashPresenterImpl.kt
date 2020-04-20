package com.sch.playandroid.presenter

import android.content.Context
import com.sch.playandroid.contract.SplashPresenterConstant
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission

class SplashPresenterImpl(var view: SplashPresenterConstant.ISplashView) :
    SplashPresenterConstant.ISplashPresenter {
    override fun requestPermission(context: Context) {
        AndPermission.with(context).runtime().permission(Permission.Group.STORAGE)
            .onGranted {
                view?.onSuccess()
            }
            .onDenied {
                view?.onFail()
            }
            .start()
    }

}