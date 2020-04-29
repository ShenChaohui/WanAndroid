package com.sch.playandroid.ui.main.discover.system

import com.sch.playandroid.entity.SystemBean

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class SystemContract {
    interface ISystemPresenter {
        fun getSystemData()
    }

    interface ISystemView {
        fun setSystemData(list: MutableList<SystemBean>)
        fun onError(ex: String)
    }
}