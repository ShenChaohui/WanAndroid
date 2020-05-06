package com.sch.playandroid.ui.main.tab

import com.sch.playandroid.base.IBasePresenter
import com.sch.playandroid.base.IBaseView
import com.sch.playandroid.entity.TabTypeBean

/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class TabContract {
    interface IPresenter :IBasePresenter{
        fun getTabListData(type: Int)
    }

    interface IView :IBaseView{
        fun setTabListData(list: MutableList<TabTypeBean>)
    }
}