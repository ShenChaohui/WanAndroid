package com.sch.wanandroid.ui.regist

import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView

/**
 * Created by Sch.
 * Date: 2020/4/24
 * description:
 */
class RegisterConstant {
    interface IPresenter : IBasePresenter {
        fun doRegister(username: String, password: String, rePassword: String)
    }

    interface IView : IBaseView {
        fun registerSuccess()
    }
}