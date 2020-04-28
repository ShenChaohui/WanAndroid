package com.sch.playandroid.ui.coin

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.playandroid.R
import com.sch.playandroid.adapter.CoinRecordAdapter
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.CoinRecordBean
import kotlinx.android.synthetic.main.activity_coin.*
import kotlinx.android.synthetic.main.activity_coin.ivBack
import kotlinx.android.synthetic.main.activity_coin.loadingTip
import kotlinx.android.synthetic.main.activity_coin.smartRefresh
import kotlinx.android.synthetic.main.activity_issue.*

/**
 * Created by Sch.
 * Date: 2020/4/26
 * description:积分页面
 */
class CoinActivity : BaseActivity(), CoinConstant.ICoinView {
    private val presenterImpl by lazy { CoinPresenterImpl(this) }
    private val coinRecordList by lazy { mutableListOf<CoinRecordBean>() }
    private val adapter by lazy { CoinRecordAdapter() }
    private var pageNum: Int = 1
    private var myCoin: Int? = 0
    override fun init(savedInstanceState: Bundle?) {
        val bundle: Bundle? = intent.extras
        myCoin = bundle?.getInt(Constants.MY_COIN)
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = adapter
        smartRefresh.setOnLoadMoreListener {
            pageNum++
            presenterImpl?.getCoinList(pageNum)
        }
        ivBack.setOnClickListener {
            finish()
        }
        loadingTip.loading()
        // 设置无网络时重新加载点击事件
        loadingTip.setReloadListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loadingTip.loading()
                presenterImpl?.getCoinList(pageNum)
            }
        })
        presenterImpl?.getCoinList(pageNum)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_coin
    }

    override fun showCoinList(list: MutableList<CoinRecordBean>) {
        dismissRefresh()
        startAnim()
        if (list?.isNotEmpty()) {
            coinRecordList.addAll(list)
            adapter.updata(coinRecordList)
        } else {
            if (coinRecordList.size == 0) loadingTip.showEmpty()
            else SmartToast.info("没有更多数据了")
        }

    }

    private fun startAnim() {
        myCoin?.let {
            val animator = ValueAnimator.ofInt(0, myCoin!!)
            animator.duration = 1500
            animator.interpolator = DecelerateInterpolator()
            animator.addUpdateListener {
                val currentValue = it.animatedValue as Int
                tvMyCoin.text = "$currentValue"
            }
            animator.start()
        }
    }

    override fun onError(ex: String) {
        //请求失败将page -1
        if (pageNum > 0) pageNum--
        dismissRefresh()
        if (coinRecordList.size == 0) {
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
}