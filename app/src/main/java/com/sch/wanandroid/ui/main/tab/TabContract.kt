package com.sch.wanandroid.ui.main.tab

import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView
import com.sch.wanandroid.entity.TabTypeBean

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