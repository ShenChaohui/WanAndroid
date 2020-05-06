package com.sch.wanandroid.ui.my.articles

import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView
import com.sch.wanandroid.entity.ArticleBean

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class MyArticlesContract {
    interface IPresenter:IBasePresenter {
        fun getArticleData(pageNum: Int)
        fun deleteArticle(id: Int)
        fun addArticle(title: String, link: String)
    }

    interface IView :IBaseView{
        fun setArticleData(list: MutableList<ArticleBean>)
        fun deleteArticleSuccess()
        fun addArticleSuccess()
    }
}