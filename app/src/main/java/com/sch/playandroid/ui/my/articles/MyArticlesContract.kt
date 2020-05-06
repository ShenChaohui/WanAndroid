package com.sch.playandroid.ui.my.articles

import android.icu.text.CaseMap
import com.sch.playandroid.base.IBasePresenter
import com.sch.playandroid.base.IBaseView
import com.sch.playandroid.entity.ArticleBean

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