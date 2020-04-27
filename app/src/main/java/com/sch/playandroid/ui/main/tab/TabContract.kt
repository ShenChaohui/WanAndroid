package com.sch.playandroid.ui.main.tab

import com.sch.playandroid.entity.TabTypeBean

/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class TabContract {
    interface ITabPresenter {
        fun getTabList(type: Int)
    }

    interface ITabView {
        fun setTabList(list: MutableList<TabTypeBean>)
        fun setError(ex: String)
    }
}