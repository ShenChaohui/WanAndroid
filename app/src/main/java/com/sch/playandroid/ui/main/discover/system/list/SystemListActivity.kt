package com.sch.playandroid.ui.main.discover.system.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sch.playandroid.R
import com.sch.playandroid.adapter.ArticleAdapter
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.ui.web.WebActivity
import kotlinx.android.synthetic.main.activity_system_list.*
import kotlinx.android.synthetic.main.activity_system_list.loadingTip
import kotlinx.android.synthetic.main.activity_system_list.refreshLayout
import kotlinx.android.synthetic.main.activity_system_list.rvList
import kotlinx.android.synthetic.main.fragment_refresh_list.*

/**
 * Created by Sch.
 * Date: 2020/4/23
 * description:
 */
class SystemListActivity : BaseActivity(), SystemListContract.ISystemListView {
    private var title: String? = null
    private var cid: Int? = null
    private val adapter by lazy { ArticleAdapter() }
    private val presenterImpl by lazy { SystemListPresenterImpl(this) }
    private var curPage = 0
    val articleList by lazy { ArrayList<ArticleBean>() }
    override fun init(savedInstanceState: Bundle?) {
        val bundle: Bundle? = intent.extras
        cid = bundle?.getInt(Constants.SYSTEM_ID)
        title = bundle?.getString(Constants.SYSTEM_TITLE)
        tvTitle.text = title
        ivBack.setOnClickListener {
            finish()
        }
        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = adapter
        rvList.overScrollMode = RecyclerView.OVER_SCROLL_NEVER//取消滑动到顶部边界越界效果
        adapter.setOnItemClickListener(object : ArticleAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                intent(Bundle().apply {
                    putString(Constants.WEB_URL, articleList[position].link)
                    putString(Constants.WEB_TITLE, articleList[position].title)
                }, WebActivity::class.java, false)
            }
        })
//加载中动画
        loadingTip.loading()
        // 设置无网络时重新加载点击事件
        loadingTip.setReloadListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loadingTip.loading()
                cid?.let { presenterImpl.getListData(curPage, it) }
            }
        })
        //下拉监听
        refreshLayout.setOnRefreshListener {
            curPage = 0
            articleList.clear()
            adapter.updata(articleList)
            cid?.let { it1 -> presenterImpl.getListData(curPage, it1) }

        }
        refreshLayout.setOnLoadMoreListener {
            curPage++
            cid?.let { it1 -> presenterImpl.getListData(curPage, it1) }
        }
        cid?.let { presenterImpl.getListData(curPage, it) }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_system_list
    }

    override fun setListData(list: List<ArticleBean>) {
        loadingTip.dismiss()
        articleList.addAll(list)
        adapter.updata(articleList)
        if (curPage == 0) {
            refreshLayout.finishRefresh()
        } else {
            refreshLayout.finishLoadMore()
        }
    }

    override fun setError(ex: String) {
        loadingTip.showInternetError()
    }
}