package com.sch.playandroid.ui.issue

import com.sch.playandroid.base.IBasePresenter
import com.sch.playandroid.base.IBaseView
import com.sch.playandroid.entity.ArticleBean

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class IssueContract {
    interface IPresenter:IBasePresenter{
        fun getArticleData(pageNum: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }
    interface IView:IBaseView{
        fun setArticleData(list: MutableList<ArticleBean>)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}