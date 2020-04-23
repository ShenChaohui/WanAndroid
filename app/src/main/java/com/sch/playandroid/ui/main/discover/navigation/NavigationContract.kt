package com.sch.playandroid.ui.main.discover.navigation

import com.sch.playandroid.entity.NavigationBean
import com.sch.playandroid.entity.SystemBean


/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class NavigationContract {
    interface INavigationPresenter {
        fun getNavigationData()
    }

    interface INavigationView {
        fun setNavigationData(list: List<NavigationBean>)
        fun setError(ex: String)
    }
}