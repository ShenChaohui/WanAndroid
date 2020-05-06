package com.sch.playandroid.ui.login

import com.sch.playandroid.base.IBasePresenter
import com.sch.playandroid.base.IBaseView

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