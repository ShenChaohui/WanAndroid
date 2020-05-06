package com.sch.playandroid.ui.my.rank

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.playandroid.R
import com.sch.playandroid.adapter.RankAdapter
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.RankBean
import com.sch.playandroid.ui.web.WebActivity
import kotlinx.android.synthetic.main.activity_rank.*
import kotlinx.android.synthetic.main.activity_rank.ivBack
import kotlinx.android.synthetic.main.activity_rank.loadingTip
import kotlinx.android.synthetic.main.activity_rank.smartRefresh

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:
 */
class RankActivity : BaseActivity<RankConstant.IPresenter>(),
    RankConstant.IView {
    private var pageNum = 1
    private val rankList by lazy { mutableListOf<RankBean>() }
    private val rankAdapter by lazy { RankAdapter() }

    /**
     * 我的积分
     */
    private var myCoin: Int? = null

    /**
     * 我的排名
     */
    private var myRank: Int? = null
    override fun init(savedInstanceState: Bundle?) {
        val bundle: Bundle? = intent.extras
        myCoin = bundle?.getInt(Constants.MY_COIN)
        myRank = bundle?.getInt(Constants.MY_RANK)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = rankAdapter
        recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        initMyRank()
        initListener()
        loadingTip.loading()
        loadData()
    }

    private fun initListener() {
        smartRefresh.setOnRefreshListener {
            loadData()
        }
        smartRefresh.setOnLoadMoreListener {
            pageNum++
            mPresenter?.getRankData(pageNum)
        }
        ivBack.setOnClickListener {
            finish()
        }
        ivDetail.setOnClickListener {
            intent(Bundle().apply {
                putString(Constants.WEB_URL, "https://www.wanandroid.com/blog/show/2653")
                putString(Constants.WEB_TITLE, "积分规则")
            }, WebActivity::class.java, false)
        }
        // 设置无网络时重新加载点击事件
        loadingTip.setReloadListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loadingTip.loading()
                loadData()
            }
        })
    }

    private fun loadData() {
        rankList.clear()
        rankAdapter.updata(rankList)
        pageNum = 1
        mPresenter?.getRankData(pageNum)
    }

    private fun initMyRank() {
        tvCoinCount.text = "$myCoin"
        tvRank.text = "$myRank"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_rank
    }

    override fun setRankData(list: MutableList<RankBean>) {
        dismissRefresh()
        if (list.isNotEmpty()) {
            rankList.addAll(list)
            rankAdapter.updata(rankList)
        } else {
            if (rankList.size == 0) loadingTip.showEmpty()
            else SmartToast.info("没有更多数据了")
        }

    }

    override fun onError(ex: String) {
        //请求失败将page -1
        if (pageNum > 1) pageNum--
        dismissRefresh()
        if (rankList.size == 0) {
            loadingTip.showInternetError()
        }
        SmartToast.error(ex)
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

    override fun createPresenter(): RankConstant.IPresenter? {
        return RankPresenterImpl()
    }
}