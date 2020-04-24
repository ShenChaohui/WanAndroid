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
        fun showBanner(list: List<BannerBean>)
        fun setTopArticleDatas(list: List<ArticleBean>)
        fun onLoadArticleDatas(list: List<ArticleBean>)
        fun onError(msg: String)
        fun oncollectError(msg: String)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}