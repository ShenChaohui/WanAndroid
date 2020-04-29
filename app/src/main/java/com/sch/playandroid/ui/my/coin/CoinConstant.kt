package com.sch.playandroid.ui.my.coin

import com.sch.playandroid.entity.CoinRecordBean

/**
 * Created by Sch.
 * Date: 2020/4/27
 * description:
 */
class CoinConstant {
    interface ICoinPresenter {
        fun getCoinData(pageNum: Int)
    }

    interface ICoinView {
        fun setCoinData(list: MutableList<CoinRecordBean>)
        fun onError(ex: String)
    }
}