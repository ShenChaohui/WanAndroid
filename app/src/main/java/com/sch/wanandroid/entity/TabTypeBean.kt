package com.sch.wanandroid.entity

/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
data class TabTypeBean(
    var children: List<Any>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
)