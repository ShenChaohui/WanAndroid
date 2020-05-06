package com.sch.wanandroid.entity

/**
 * Created by Sch.
 * Date: 2020/4/24
 * description:
 */
data class UserBean(
    var admin: Boolean,
    var chapterTops: List<String>,
    var collectIds: List<Int>,
    var email: String,
    var icon: String,
    var id: Int,
    var nickname: String,
    var password: String,
    var publicName: String,
    var token: String,
    var type: Int,
    var username: String
)
