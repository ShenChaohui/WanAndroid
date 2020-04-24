package com.sch.playandroid.ui.login

/**
 * Created by Sch.
 * Date: 2020/4/24
 * description:
 */
class LoginConstant {
    interface ILoginPresenter {
        fun doLogin(username: String, password: String)
    }

    interface ILoginView {
        fun loginSuccess()
        fun onError(ex:String)
    }
}