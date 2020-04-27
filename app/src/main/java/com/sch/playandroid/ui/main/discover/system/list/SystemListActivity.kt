package com.sch.playandroid.ui.main.discover.system.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.playandroid.R
import com.sch.playandroid.adapter.ArticleAdapter
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.ArticleBean
import com.sch.playandroid.ui.web.WebActivity
import com.sch.playandroid.util.AppManager
import kotlinx.android.synthetic.main.activity_system_list.*
import kotlinx.android.synthetic.main.activity_system_list.loadingTip
import kotlinx.android.synthetic.main.activity_system_list.rvList

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
    private var pageNum = 0
    val articleList by lazy { mutableListOf<ArticleBean>() }

    private var collectPosition = 0

    /**
     * 点击收藏后将点击事件上锁,等接口有相应结果再解锁
     * 避免重复点击产生的bug  false表示没锁，true表示锁住
     */
    private var lockCollectClick = false
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
        adapter.setOnCollectClickListener(object : ArticleAdapter.OnCollectClickListener {
            override fun onCollectClick(position: Int) {
                if (!AppManager.isLogin()) {
                    SmartToast.info("请先登录")
                    return
                }
                if (position < articleList.size && !lockCollectClick) {
                    lockCollectClick = true
                    collectPosition = position
                    articleList[position].apply {
                        if (!collect) {
                            presenterImpl?.collect(id)
                        } else {
                            presenterImpl?.unCollect(id)
                        }

                    }

                }
            }
        })
//加载中动画
        loadingTip.loading()
        // 设置无网络时重新加载点击事件
        loadingTip.setReloadListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loadingTip.loading()
                cid?.let { presenterImpl.getListData(pageNum, it) }
            }
        })
        //下拉监听
        smartRefresh.setOnRefreshListener {
            pageNum = 0
            articleList.clear()
            adapter.updata(articleList)
            cid?.let { it1 -> presenterImpl.getListData(pageNum, it1) }

        }
        smartRefresh.setOnLoadMoreListener {
            pageNum++
            cid?.let { it1 -> presenterImpl.getListData(pageNum, it1) }
        }
        cid?.let { presenterImpl.getListData(pageNum, it) }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_system_list
    }

    override fun setListData(list: MutableList<ArticleBean>) {
        dismissRefresh()
        if (list.isNotEmpty()) {
            articleList.addAll(list)
            adapter.updata(articleList)
        } else {
            if (articleList.size == 0) loadingTip.showEmpty()
            else SmartToast.info("没有更多数据了")
        }
    }

    override fun setError(ex: String) {
        lockCollectClick = false
        //请求失败将page -1
        if (pageNum > 0) pageNum--
        dismissRefresh()
        if (articleList.size == 0) {
            loadingTip.showInternetError()
        }
        SmartToast.error(ex)
    }

    override fun collectSuccess() {
        lockCollectClick = false
        if (collectPosition < articleList.size) {
            articleList[collectPosition].collect = true
            adapter.updata(articleList)
        }
    }

    override fun unCollectSuccess() {
        lockCollectClick = false
        if (collectPosition < articleList.size) {
            articleList[collectPosition].collect = false
            adapter.updata(articleList)
        }
    }
    /**
     * 隐藏刷新加载
     */
    private fun dismissRefresh() {
        loadingTip.dismiss()
        if (smartRefresh.state.isOpening) {
            smartRefresh.finishLoadMore()
            smartRefresh.finishRefresh()
        }
    }
}