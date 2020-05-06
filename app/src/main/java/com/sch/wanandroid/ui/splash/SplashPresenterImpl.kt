package com.sch.wanandroid.ui.splash

import android.content.Context
import com.sch.wanandroid.base.BasePresenter
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission

class SplashPresenterImpl:BasePresenter<SplashConstant.IView>(),
    SplashConstant.IPresenter {
    override fun requestPermission(context: Context) {
        AndPermission.with(context).runtime().permission(Permission.Group.STORAGE)
            .onGranted {
                getView()?.onSuccess()
            }
            .onDenied {
                getView()?.onError("用户拒绝")
            }
            .start()
    }

}