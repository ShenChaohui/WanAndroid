package com.sch.playandroid.widget

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.sch.playandroid.R
import com.sch.playandroid.entity.HomeArticleBean
import kotlinx.android.synthetic.main.item_home_article.view.*

class HomeArticleItemView(context: Context) : RelativeLayout(context) {
    fun setData(data: HomeArticleBean) {
        tvTitle.setText(data.title)
        tvAuthor.setText(data.shareUser)
        tvDate.setText(data.niceDate)
        tvChapterName.setText(data.superChapterName)

    }

    init {
        View.inflate(context, R.layout.item_home_article, this)
    }


}
