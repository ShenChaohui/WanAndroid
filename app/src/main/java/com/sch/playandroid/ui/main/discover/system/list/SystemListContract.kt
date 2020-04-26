package com.sch.playandroid.ui.main.discover.system.list

import com.sch.playandroid.entity.ArticleBean

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class SystemListContract {
    interface ISystemListPresenter{
        fun getListData(curPage: Int, cid: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }
    interface ISystemListView{
        fun setListData(list: List<ArticleBean>)
        fun setError(ex: String)
        fun oncollectError(msg: String)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}