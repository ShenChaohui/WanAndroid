package com.sch.playandroid.util

import android.content.Context
import com.sch.playandroid.PlayAndroidApplication

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