package com.sch.playandroid.ui.set

import android.content.Context
import com.sch.playandroid.base.IBasePresenter
import com.sch.playandroid.base.IBaseView

/**
 * Created by Sch.
 * Date: 2020/4/27
 * description:
 */
class SetContract {
    interface IPresenter:IBasePresenter {
        fun logout()
        fun clearCache(context: Context)
        fun checkUpdate()
    }

    interface IView :IBaseView{
        fun logoutSuccess()
        fun clearCacheSuccess()
        fun checkUpdateSuccess()
    }
}