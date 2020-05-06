package com.sch.wanandroid.entity

/**
 * Created by Sch.
 * Date: 2020/4/27
 * description:
 */
data class CoinRecordBean(
    var coinCount: Int,
    var date: Long,
    var desc: String,
    var id: Int,
    var reason: String,
    var type: Int,
    var userId: Int,
    var userName: String
)