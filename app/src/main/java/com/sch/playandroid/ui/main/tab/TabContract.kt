package com.sch.playandroid.ui.main.tab

import com.sch.playandroid.entity.TabTypeBean

/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class TabContract {
    interface ITabPresenter {
        fun getTabListData(type: Int)
    }

    interface ITabView {
        fun setTabListData(list: MutableList<TabTypeBean>)
        fun onError(ex: String)
    }
}