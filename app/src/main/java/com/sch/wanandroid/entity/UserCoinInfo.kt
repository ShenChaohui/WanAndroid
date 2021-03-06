package com.sch.wanandroid.entity

import com.sch.wanandroid.constants.Constants
import com.sch.wanandroid.util.GsonUtil
import com.sch.wanandroid.util.PrefUtils

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:
 */
data class UserCoinInfo(
    var coinCount: Int,
    var level: Int,
    var rank: Int,
    var userId: Int,
    var username: String
) {
    companion object {
        fun getUserCoinInfo(): UserCoinInfo? {
            var userCoinInfo: UserCoinInfo? = null
            PrefUtils.getString(Constants.USERCOININFO)?.let {
                userCoinInfo = GsonUtil.parseJsonWithGson(it, UserCoinInfo::class.java)
            }
            return userCoinInfo
        }
    }
}
