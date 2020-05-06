package com.sch.wanandroid.ui.main.discover.navigation

import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView
import com.sch.wanandroid.entity.NavigationBean


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