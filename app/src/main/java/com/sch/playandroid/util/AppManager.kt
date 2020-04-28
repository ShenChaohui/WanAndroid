package com.sch.playandroid.util

import android.content.Context
import com.sch.playandroid.constants.Constants
import java.lang.Exception


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

        /**
         * 获取当前程序版本名
         *
         * @param context
         * @return
         */
        fun getVersionName(context: Context): String {
            var versionName = "1.0"
            try {
                versionName =
                    context.packageManager.getPackageInfo(context.packageName, 0).versionName
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return versionName
        }

        /**
         * 获取当前程序版本号
         *
         * @param context
         * @return
         */
        fun getVersionCode(context: Context): Int {
            var versionCode = 1
            try {
                versionCode =
                    context.packageManager.getPackageInfo(context.packageName, 0).versionCode
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return versionCode
        }
    }
}