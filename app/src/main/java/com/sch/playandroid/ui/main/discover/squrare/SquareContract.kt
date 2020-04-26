package com.sch.playandroid.ui.main.discover.squrare

import com.sch.playandroid.entity.ArticleBean


/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class SquareContract {
    interface ISquarePresenter {
        fun getListData(curPage: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }

    interface ISquareView {
        fun setListData(list: List<ArticleBean>)
        fun setError(ex: String)
        fun oncollectError(msg: String)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}