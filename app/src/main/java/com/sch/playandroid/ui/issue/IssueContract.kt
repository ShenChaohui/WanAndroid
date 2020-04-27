package com.sch.playandroid.ui.issue

import com.sch.playandroid.entity.ArticleBean

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class IssueContract {
    interface IIssuePresenter{
        fun getListData(curPage: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }
    interface IIssueView{
        fun setListData(list: MutableList<ArticleBean>)
        fun setError(ex: String)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}