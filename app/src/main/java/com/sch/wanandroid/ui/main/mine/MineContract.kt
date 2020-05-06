package com.sch.wanandroid.ui.main.mine

import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.base.IBaseView
import com.sch.wanandroid.entity.UserCoinInfo

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