package com.sch.playandroid.ui.set

/**
 * Created by Sch.
 * Date: 2020/4/27
 * description:
 */
class SetContract {
    interface ISetPresenter {
        fun logout()
    }

    interface ISetView {
        fun logoutSuccess()
        fun onError(ex: String)
    }
}