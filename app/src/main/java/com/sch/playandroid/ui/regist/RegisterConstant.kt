package com.sch.playandroid.ui.regist

/**
 * Created by Sch.
 * Date: 2020/4/24
 * description:
 */
class RegisterConstant {
    interface IRegisterPresenter {
        fun doRegister(username: String, password: String, rePassword: String)
    }

    interface IRegisterView {
        fun registerSuccess()
        fun onError(ex: String)
    }
}