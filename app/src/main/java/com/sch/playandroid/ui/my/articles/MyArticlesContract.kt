package com.sch.playandroid.ui.my.articles

import android.icu.text.CaseMap
import com.sch.playandroid.entity.ArticleBean

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class MyArticlesContract {
    interface IMyArticlesPresenter {
        fun getArticleData(pageNum: Int)
        fun deleteArticle(id: Int)
        fun addArticle(title: String, link: String)
    }

    interface IMyArticlesView {
        fun setArticleData(list: MutableList<ArticleBean>)
        fun onError(ex: String)
        fun deleteArticleSuccess()
        fun addArticleSuccess()
    }
}