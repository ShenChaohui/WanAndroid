package com.sch.playandroid.ui.main.tab.list

import com.sch.playandroid.base.IBasePresenter
import com.sch.playandroid.base.IBaseView
import com.sch.playandroid.entity.ArticleBean


/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class TabListContract {
    interface IPresenter : IBasePresenter {
        fun getArticleData(type: Int, pageNum: Int, cid: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }

    interface IView : IBaseView {
        fun setArticleData(list: MutableList<ArticleBean>)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}