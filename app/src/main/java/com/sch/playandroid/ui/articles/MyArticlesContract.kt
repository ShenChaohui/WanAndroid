package com.sch.playandroid.ui.articles

import android.icu.text.CaseMap
import com.sch.playandroid.entity.ArticleBean

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class MyArticlesContract {
    interface IMyArticlesPresenter {
        fun getListData(curPage: Int)
        fun deleteArticle(id: Int)
        fun addArticle(title: String, link: String)
    }

    interface IMyArticlesView {
        fun setListData(list: MutableList<ArticleBean>)
        fun setError(ex: String)
        fun deleteSuccess()
        fun addArticleSuccess()
    }
}