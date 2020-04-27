package com.sch.playandroid.ui.main.mine

import android.os.Bundle
import com.sch.playandroid.R
import com.sch.playandroid.base.LazyFragment
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.UserCoinInfo
import com.sch.playandroid.ui.coin.CoinActivity
import com.sch.playandroid.ui.issue.IssueActivity
import com.sch.playandroid.ui.login.LoginActivity
import com.sch.playandroid.ui.rank.RankActivity
import com.sch.playandroid.ui.set.SetActivity
import com.sch.playandroid.util.AppManager
import com.zs.wanandroid.event.LoginEvent
import com.zs.wanandroid.event.LogoutEvent
import kotlinx.android.synthetic.main.fragment_mine.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MineFragment : LazyFragment(), MineContract.IMineView {
    val presenterImpl by lazy { MinePresenterImpl(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun lazyInit() {
        if (AppManager.isLogin()) {
            AppManager.getUserCoinInfo()?.let {
                setUserCoinInfo(it)
            }
            presenterImpl.getUserCoinInfo()
        }
        initListener()
    }

    private fun initListener() {
        tvUserName.setOnClickListener {
            if (!AppManager.isLogin()) {
                intent(LoginActivity::class.java, false)
            }
        }

        llSet.setOnClickListener {
            intent(SetActivity::class.java, false)
        }
        llCoin.setOnClickListener {
            intent(Bundle().apply {
                putInt(Constants.MY_COIN, Integer.valueOf(tvCoinCount.text.toString()))
            }
                , CoinActivity::class.java, true)
        }
        llRank.setOnClickListener {
            intent(Bundle().apply {
                putInt(Constants.MY_COIN, Integer.valueOf(tvCoinCount.text.toString()))
                putInt(Constants.MY_RANK, Integer.valueOf(tvRank.text.toString()))
            }
                , RankActivity::class.java, true)
        }
        llIssue.setOnClickListener {
            intent(IssueActivity::class.java, false)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun setUserCoinInfo(userCoinInfo: UserCoinInfo) {
        userCoinInfo?.apply {
            tvUserName.text = username
            tvId.text = "$userId"
            tvRank.text = "$rank"
            tvCoinCount.text = "$coinCount"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    /**
     * 登陆消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun loginEvent(loginEvent: LoginEvent) {
        presenterImpl?.getUserCoinInfo()
    }

    /**
     * 退出消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun logoutEvent(loginEvent: LogoutEvent) {
        tvUserName.text = "请先登录"
        tvId.text = "--"
        tvRank.text = "0"
        tvCoinCount.text = "0"
    }
}