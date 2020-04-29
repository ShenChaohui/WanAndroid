package com.sch.playandroid.ui.main.mine

import com.sch.playandroid.entity.UserCoinInfo

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:
 */
class MineContract {
    interface IMinePresenter {
        fun getUserCoinInfo()

    }

    interface IMineView {
        fun setUserCoinInfo(userCoinInfo: UserCoinInfo)
        fun onError(ex: String)
    }
}