package com.sch.wanandroid.ui.my.rank

import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView
import com.sch.wanandroid.entity.RankBean

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:
 */
class RankConstant {
    interface IPresenter : IBasePresenter {
        fun getRankData(pageNum: Int)
    }

    interface IView : IBaseView {
        fun setRankData(list: MutableList<RankBean>)
    }
}