package com.sch.playandroid.ui.main.tab.list

import com.sch.playandroid.entity.ArticleBean


/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class TabListContract {
    interface ITabListPresenter {
        fun getListData(type: Int, curPage: Int, cid: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }

    interface ITabListView {
        fun setListData(list: List<ArticleBean>)
        fun setError(ex: String)
        fun oncollectError(msg: String)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}