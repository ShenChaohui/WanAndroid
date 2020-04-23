package com.sch.playandroid.ui.main.discover.navigation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sch.lolcosmos.base.BaseFragment
import com.sch.playandroid.R
import com.sch.playandroid.adapter.NavigationAdapter
import com.sch.playandroid.base.LazyFragment
import com.sch.playandroid.constants.Constants
import com.sch.playandroid.entity.NavigationBean
import com.sch.playandroid.ui.web.WebActivity
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * 导航
 */

class NavigationFragment : LazyFragment(), NavigationContract.INavigationView {
    private val adapter by lazy { NavigationAdapter() }
    private val navigationList by lazy { ArrayList<NavigationBean>() }
    private val presenter by lazy { NavigationPresenterImpl(this) }

    override fun lazyInit() {
        rvList.layoutManager = LinearLayoutManager(context)
        rvList.adapter = adapter
        adapter.setStstemClickListener(object : NavigationAdapter.OnNavigationClickListener {
            override fun onCollectClick(navigationPosition: Int, navigationChildrenPosition: Int) {
                intent(Bundle().apply {
                    putString(
                        Constants.WEB_TITLE,
                        navigationList[navigationPosition].articles[navigationChildrenPosition].title
                    )
                    putString(
                        Constants.WEB_URL,
                        navigationList[navigationPosition].articles[navigationChildrenPosition].link
                    )
                }, WebActivity::class.java, false)
            }
        })
        presenter.getNavigationData()
        //加载中动画
        loadingTip.loading()
        // 设置无网络时重新加载点击事件
        loadingTip.setReloadListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                loadingTip.loading()
                presenter.getNavigationData()
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun setNavigationData(list: List<NavigationBean>) {
        loadingTip.dismiss()
        navigationList.clear()
        navigationList.addAll(list)
        adapter.updata(navigationList)
    }

    override fun setError(ex: String) {
        loadingTip.showInternetError()
    }

}