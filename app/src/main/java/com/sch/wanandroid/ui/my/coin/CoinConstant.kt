package com.sch.wanandroid.ui.my.coin

import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView
import com.sch.wanandroid.entity.CoinRecordBean

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