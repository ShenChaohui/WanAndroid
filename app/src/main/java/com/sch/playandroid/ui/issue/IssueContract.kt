package com.sch.playandroid.ui.issue

import com.sch.playandroid.entity.ArticleBean

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class IssueContract {
    interface IIssuePresenter{
        fun getArticleData(pageNum: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }
    interface IIssueView{
        fun setArticleData(list: MutableList<ArticleBean>)
        fun onError(ex: String)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}