package com.sch.playandroid.ui.main.discover.system

import com.sch.playandroid.base.IBasePresenter
import com.sch.playandroid.base.IBaseView
import com.sch.playandroid.entity.SystemBean

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class SystemContract {
    interface IPresenter : IBasePresenter {
        fun getSystemData()
    }

    interface IView : IBaseView {
        fun setSystemData(list: MutableList<SystemBean>)
    }
}