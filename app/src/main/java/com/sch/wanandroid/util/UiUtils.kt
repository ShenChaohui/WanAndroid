package com.sch.wanandroid.util

import android.content.Context

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
object UiUtils {
    fun dip2px(context: Context, dpValue: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dpValue * density + 0.5).toInt()
    }

    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }
}