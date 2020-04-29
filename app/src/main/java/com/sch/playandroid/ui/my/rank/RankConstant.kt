package com.sch.playandroid.ui.my.rank

import com.sch.playandroid.entity.RankBean

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:
 */
class RankConstant {
    interface IRankPresenter {
        fun getRankData(pageNum: Int)
    }

    interface IRankView {
        fun setRankData(list: MutableList<RankBean>)
        fun onError(ex: String)
    }
}