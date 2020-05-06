package com.sch.playandroid.ui.main.home

import com.sch.playandroid.base.IBasePresenter
import com.sch.playandroid.base.IBaseView
import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.entity.BannerBean


class HomeContract {

    interface IPresenter :IBasePresenter{
        fun getBannerData()
        fun getTopArticleData()
        fun getArticleData(pageNum: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }

    interface IView :IBaseView{
        fun setBannerData(list: MutableList<BannerBean>)
        fun setTopArticleData(list: MutableList<ArticleBean>)
        fun setArticleData(list: MutableList<ArticleBean>)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}