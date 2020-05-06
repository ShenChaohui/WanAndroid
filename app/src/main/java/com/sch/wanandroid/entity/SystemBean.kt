package com.sch.wanandroid.entity

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description: 体系bean
 */
data class SystemBean(
    var children: List<SystemChildrenBean>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
)

data class SystemChildrenBean(
    var children: List<Any>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
)