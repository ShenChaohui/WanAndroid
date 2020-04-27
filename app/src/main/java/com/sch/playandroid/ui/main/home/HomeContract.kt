package com.sch.playandroid.ui.main.home

import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.entity.BannerBean


class HomeContract {

    interface IHomePresenter {
        fun getBannerData()
        fun getTopArticleData()
        fun getArticleData(index: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }

    interface IHomeView {
        fun showBanner(list: MutableList<BannerBean>)
        fun setTopArticleDatas(list: MutableList<ArticleBean>)
        fun onLoadArticleDatas(list: MutableList<ArticleBean>)
        fun onError(msg: String)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}