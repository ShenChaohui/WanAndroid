package com.sch.playandroid.ui.my.collect

import com.sch.playandroid.entity.CollectBean

/**
 * Created by Sch.
 * Date: 2020/4/28
 * description:
 */
class MyCollectContract {
    interface IMyCollectPresenter {
        fun getCollectList(pageNum: Int)
        fun unCollect(id: Int, originId: Int)
        fun addCollect(title: String, author: String?, link: String)
    }

    interface IMyCollectView {
        fun showCollectList(list: MutableList<CollectBean>)
        fun onError(ex: String)
        fun unCollectSuccess()
        fun addCollectSuccess()
    }
}