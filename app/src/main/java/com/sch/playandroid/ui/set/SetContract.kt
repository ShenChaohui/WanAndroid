package com.sch.playandroid.ui.set

import android.content.Context

/**
 * Created by Sch.
 * Date: 2020/4/27
 * description:
 */
class SetContract {
    interface ISetPresenter {
        fun logout()
        fun clearCache(context: Context)
        fun checkUpdate()
    }

    interface ISetView {
        fun logoutSuccess()
        fun clearCacheSuccess()
        fun checkUpdateSuccess()
        fun onError(ex: String)
    }
}