package com.sch.playandroid.ui.coin

import com.sch.playandroid.entity.CoinRecordBean

/**
 * Created by Sch.
 * Date: 2020/4/27
 * description:
 */
class CoinConstant {
    interface ICoinPresenter {
        fun getCoinList(pageNum: Int)
    }

    interface ICoinView {
        fun showCoinList(list: MutableList<CoinRecordBean>)
        fun onError(ex: String)
    }
}