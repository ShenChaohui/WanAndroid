package com.sch.playandroid.ui.my.coin

import com.sch.playandroid.base.IBasePresenter
import com.sch.playandroid.base.IBaseView
import com.sch.playandroid.entity.CoinRecordBean

/**
 * Created by Sch.
 * Date: 2020/4/27
 * description:
 */
class CoinConstant {
    interface IPresenter : IBasePresenter {
        fun getCoinData(pageNum: Int)
    }

    interface IView : IBaseView {
        fun setCoinData(list: MutableList<CoinRecordBean>)
    }
}