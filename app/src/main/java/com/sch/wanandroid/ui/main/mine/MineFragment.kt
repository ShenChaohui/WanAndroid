package com.sch.wanandroid.ui.main.mine

import android.os.Bundle
import com.coder.zzq.smartshow.toast.SmartToast
import com.sch.lolcosmos.base.BaseFragment
import com.sch.wanandroid.R
import com.sch.wanandroid.constants.Constants
import com.sch.wanandroid.entity.UserCoinInfo
import com.sch.wanandroid.ui.my.articles.MyArticlesActivity
import com.sch.wanandroid.ui.my.coin.CoinActivity
import com.sch.wanandroid.ui.my.collect.MyCollectActivity
import com.sch.wanandroid.ui.issue.IssueActivity
import com.sch.wanandroid.ui.login.LoginActivity
import com.sch.wanandroid.ui.my.rank.RankActivity
import com.sch.wanandroid.ui.set.SetActivity
import com.sch.wanandroid.util.AppManager
import com.zs.wanandroid.event.LoginEvent
import com.zs.wanandroid.event.LogoutEvent
import kotlinx.android.synthetic.main.fragment_mine.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MineFragment : BaseFragment<MineContract.IPresenter>(), MineContract.IView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun init(savedInstanceState: Bundle?) {
        if (AppManager.isLogin()) {
            UserCoinInfo.getUserCoinInfo()?.let {
                setUserCoinInfo(it)
            }
            mPresenter?.getUserCoinInfo()
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
        llMyCollect.setOnClickListener {
            intent(MyCollectActivity::class.java, true)
        }
        llMyArticle.setOnClickListener {
            intent(MyArticlesActivity::class.java, true)
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun setUserCoinInfo(userCoinInfo: UserCoinInfo) {
        userCoinInfo.apply {
            tvUserName.text = username
            tvId.text = "$userId"
            tvRank.text = "$rank"
            tvCoinCount.text = "$coinCount"
        }
    }

    override fun onError(ex: String) {
        SmartToast.error(ex)
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
        mPresenter?.getUserCoinInfo()
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

    override fun createPresenter(): MineContract.IPresenter? {
        return MinePresenterImpl()
    }
}