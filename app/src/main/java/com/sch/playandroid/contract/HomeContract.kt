package com.sch.playandroid.contract

import com.sch.playandroid.entity.BannerBean
import com.sch.playandroid.entity.HomeArticleBean


class HomeContract {

    interface IHomePresenter {
        fun getBannerData()
        fun getArticleData(index: Int)
    }

    interface IHomeView {
        fun showBanner(list: List<BannerBean>)
        fun onLoadArticleDatas(list: List<HomeArticleBean>)
        fun onError(msg: String)
    }
}