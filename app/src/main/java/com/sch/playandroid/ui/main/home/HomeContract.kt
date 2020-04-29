package com.sch.playandroid.ui.main.home

import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.entity.BannerBean


class HomeContract {

    interface IHomePresenter {
        fun getBannerData()
        fun getTopArticleData()
        fun getArticleData(pageNum: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }

    interface IHomeView {
        fun setBannerData(list: MutableList<BannerBean>)
        fun setTopArticleData(list: MutableList<ArticleBean>)
        fun setArticleData(list: MutableList<ArticleBean>)
        fun onError(msg: String)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}