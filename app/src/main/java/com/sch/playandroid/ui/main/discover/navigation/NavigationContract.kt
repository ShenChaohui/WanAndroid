package com.sch.playandroid.ui.main.discover.navigation

import com.sch.playandroid.base.IBasePresenter
import com.sch.playandroid.base.IBaseView
import com.sch.playandroid.entity.NavigationBean
import com.sch.playandroid.entity.SystemBean


/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class NavigationContract {
    interface IPresenter : IBasePresenter {
        fun getNavigationData()
    }

    interface IView : IBaseView {
        fun setNavigationData(list: MutableList<NavigationBean>)
    }
}