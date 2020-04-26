package com.sch.playandroid.ui.rank

import com.sch.playandroid.entity.RankBean

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:
 */
class RankConstant {
    interface IRankPresenter {
        fun getRankList(pageNum: Int)
    }

    interface IRankView {
        fun showRankList(list: List<RankBean>)
        fun onError(ex: String)
    }
}