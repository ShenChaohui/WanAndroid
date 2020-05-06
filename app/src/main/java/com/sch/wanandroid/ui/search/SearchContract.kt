package com.sch.wanandroid.ui.search

import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView
import com.sch.wanandroid.entity.ArticleBean

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class SearchContract {
    interface IPresenter:IBasePresenter {
        fun getSearchData(keyWord: String, pageNum: Int)
        fun collect(id: Int)
        fun unCollect(id: Int)
    }

    interface IView :IBaseView{
        fun setSearchResultData(datas: MutableList<ArticleBean>)
        fun collectSuccess()
        fun unCollectSuccess()
    }
}