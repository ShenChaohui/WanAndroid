package com.sch.wanandroid.ui.login

import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView

/**
 * Created by Sch.
 * Date: 2020/4/24
 * description:
 */
class LoginConstant {
    interface IPresenter: IBasePresenter {
        fun doLogin(username: String, password: String)
    }

    interface IView: IBaseView {
        fun loginSuccess()
    }
}