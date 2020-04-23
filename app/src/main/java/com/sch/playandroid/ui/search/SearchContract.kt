package com.sch.playandroid.ui.search

import com.sch.playandroid.entity.ArticleBean

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class SearchContract {
    interface ISearchPresenter {
        fun getSearchData(keyWord: String, pageNum: Int)

    }

    interface ISearchView {
        fun setSearchResultData(datas: List<ArticleBean>)
        fun setError(ex: String)
    }
}