package com.sch.wanandroid.ui.main.discover

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sch.lolcosmos.base.BaseFragment
import com.sch.wanandroid.R
import com.sch.wanandroid.adapter.DiscoverFragmentAdapter
import com.sch.wanandroid.base.IBasePresenter
import com.sch.wanandroid.ui.main.discover.navigation.NavigationFragment
import com.sch.wanandroid.ui.main.discover.squrare.SquareFragment
import com.sch.wanandroid.ui.main.discover.system.SystemFragment
import kotlinx.android.synthetic.main.fragment_discover.*

/**
 * Created by Sch.
 * Date: 2020/4/22
 * description:发现页
 */
class DiscoverFragment : BaseFragment<IBasePresenter>() {
    private val fragmentList by lazy { ArrayList<Fragment>() }

    override fun init(savedInstanceState: Bundle?) {
        fragmentList.add(SquareFragment())
        fragmentList.add(SystemFragment())
        fragmentList.add(NavigationFragment())
        viewPager.adapter = DiscoverFragmentAdapter(fragmentList, childFragmentManager)
        viewPager.offscreenPageLimit = 3
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_discover
    }

    override fun onError(ex: String) {
    }

    override fun createPresenter(): IBasePresenter? {
        return null
    }
}