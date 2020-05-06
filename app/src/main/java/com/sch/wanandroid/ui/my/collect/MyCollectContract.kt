package com.sch.wanandroid.ui.my.collect

import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView
import com.sch.wanandroid.entity.CollectBean

/**
 * Created by Sch.
 * Date: 2020/4/28
 * description:
 */
class MyCollectContract {
    interface IPresenter : IBasePresenter {
        fun getCollectList(pageNum: Int)
        fun unCollect(id: Int, originId: Int)
        fun addCollect(title: String, author: String?, link: String)
    }

    interface IView : IBaseView {
        fun showCollectList(list: MutableList<CollectBean>)
        fun unCollectSuccess()
        fun addCollectSuccess()
    }
}