package com.sch.playandroid.ui.my.rank

import com.sch.playandroid.base.IBasePresenter
import com.sch.playandroid.base.IBaseView
import com.sch.playandroid.entity.RankBean

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