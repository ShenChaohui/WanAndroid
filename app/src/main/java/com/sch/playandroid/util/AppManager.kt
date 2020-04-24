package com.sch.playandroid.util

import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.UserBean


/**
 * des app管理类
 *
 * @author zs
 * @date 2020-03-12
 */
class AppManager {
    companion object {
        /**
         * 登录状态
         */
        fun isLogin(): Boolean {
            return PrefUtils.getBoolean(Constants.LOGIN, false)
        }

        fun getUser(): UserBean {
            return GsonUtil.parseJsonWithGson(
                PrefUtils.getString(Constants.USERINFO),
                UserBean::class.java
            )
        }
    }
}