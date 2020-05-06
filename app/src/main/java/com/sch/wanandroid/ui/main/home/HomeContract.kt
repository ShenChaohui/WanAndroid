package com.sch.wanandroid.ui.main.home

import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView
import com.sch.wanandroid.entity.ArticleBean
import com.sch.wanandroid.entity.BannerBean


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