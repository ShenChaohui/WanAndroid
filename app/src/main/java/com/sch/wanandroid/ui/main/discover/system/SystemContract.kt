package com.sch.wanandroid.ui.main.discover.system

import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView
import com.sch.wanandroid.entity.SystemBean

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