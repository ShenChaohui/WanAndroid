package com.sch.playandroid.ui.main.tab.list

import com.sch.playandroid.entity.ArticleBean


/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class TabListContract {
    interface ITabListPresenter {
        fun getArticleData(type: Int, pageNum: Int, cid: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }

    interface ITabListView {
        fun setArticleData(list: MutableList<ArticleBean>)
        fun onError(ex: String)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}