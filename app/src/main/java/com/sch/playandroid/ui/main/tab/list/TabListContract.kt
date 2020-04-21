package com.sch.playandroid.ui.main.tab.list

import com.sch.playandroid.entity.ArticleBean


/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class TabListContract {
    interface ITabListPresenter {
        fun getListData()
    }

    interface ITabListView {
        fun setListData(list:List<ArticleBean>)
    }
}