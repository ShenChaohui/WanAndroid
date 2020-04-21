package com.sch.playandroid.ui.main.tab

import com.sch.playandroid.entity.ProjectTypeBean

/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class TabContract {
    interface ITabPresenter {
        fun getTabList()
    }

    interface ITabView {
        fun setTabList(list:List<ProjectTypeBean>)
    }
}