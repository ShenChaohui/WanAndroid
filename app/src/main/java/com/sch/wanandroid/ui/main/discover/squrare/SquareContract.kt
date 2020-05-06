package com.sch.wanandroid.ui.main.discover.squrare

import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView
import com.sch.wanandroid.entity.ArticleBean


/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class SquareContract {
    interface IPresenter : IBasePresenter {
        fun getArticleData(pageNum: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }

    interface IView : IBaseView {
        fun setArticleData(list: MutableList<ArticleBean>)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}