package com.sch.playandroid.entity

/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
data class ProjectTypeBean(
    var children: List<Any>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
)