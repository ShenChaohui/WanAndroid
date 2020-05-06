package com.sch.wanandroid.ui.main.discover.system.list

import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView
import com.sch.wanandroid.entity.ArticleBean

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class SystemListContract {
    interface IPresenter:IBasePresenter{
        fun getArticleData(pageNum: Int, cid: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }
    interface IView:IBaseView{
        fun setArticleData(list: MutableList<ArticleBean>)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}