package com.sch.playandroid.ui.main.discover.squrare

import com.sch.playandroid.entity.ArticleBean


/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class SquareContract {
    interface ISquarePresenter {
        fun getArticleData(pageNum: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }

    interface ISquareView {
        fun setArticleData(list: MutableList<ArticleBean>)
        fun onError(ex: String)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}