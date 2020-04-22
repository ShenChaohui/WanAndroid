package com.sch.playandroid.ui.discover.squrare

import com.sch.playandroid.entity.ArticleBean


/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class SquareContract {
    interface ISquarePresenter {
        fun getListData(curPage: Int)
    }

    interface ISquareView {
        fun setListData(list: List<ArticleBean>)
        fun setError(ex: String)
    }
}