package com.sch.playandroid.ui.main.mine

import com.sch.playandroid.base.IBasePresenter
import com.sch.playandroid.base.IBaseView
import com.sch.playandroid.entity.UserCoinInfo

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:
 */
class MineContract {
    interface IPresenter:IBasePresenter {
        fun getUserCoinInfo()

    }

    interface IView :IBaseView{
        fun setUserCoinInfo(userCoinInfo: UserCoinInfo)
    }
}