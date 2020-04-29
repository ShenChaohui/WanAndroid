package com.sch.playandroid.ui.main.discover.system.list

import com.sch.playandroid.entity.ArticleBean

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class SystemListContract {
    interface ISystemListPresenter{
        fun getArticleData(pageNum: Int, cid: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }
    interface ISystemListView{
        fun setArticleData(list: MutableList<ArticleBean>)
        fun onError(ex: String)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}