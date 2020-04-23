package com.sch.playandroid.ui.main.discover

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sch.lolcosmos.base.BaseFragment
import com.sch.playandroid.R
import com.sch.playandroid.adapter.DiscoverFragmentAdapter
import com.sch.playandroid.ui.main.discover.navigation.NavigationFragment
import com.sch.playandroid.ui.main.discover.squrare.SquareFragment
import com.sch.playandroid.ui.main.discover.system.SystemFragment
import kotlinx.android.synthetic.main.fragment_discover.*

/**
 * Created by Sch.
 * Date: 2020/4/22
 * description:发现页
 */
class DiscoverFragment : BaseFragment() {
    private val mFragments by lazy { ArrayList<Fragment>() }

    override fun init(savedInstanceState: Bundle?) {
        mFragments.add(SquareFragment())
        mFragments.add(SystemFragment())
        mFragments.add(NavigationFragment())
        viewPager.adapter = DiscoverFragmentAdapter(mFragments, childFragmentManager)
        viewPager.offscreenPageLimit = 3
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_discover
    }
}