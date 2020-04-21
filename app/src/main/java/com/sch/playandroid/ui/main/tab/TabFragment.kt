package com.sch.playandroid.ui.main.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sch.lolcosmos.base.BaseFragment
import com.sch.playandroid.R
import com.sch.playandroid.adapter.TabFragmentAdapter
import com.sch.playandroid.entity.ProjectTypeBean
import com.sch.playandroid.ui.main.tab.list.TabListFragment
import kotlinx.android.synthetic.main.fragment_tab.*

class TabFragment : BaseFragment(), TabContract.ITabView {
    val presenter by lazy { TabPresenterImpl(this) }
    val mFragments by lazy { ArrayList<Fragment>() }

    override fun init(savedInstanceState: Bundle?) {
        presenter.getTabList()
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_tab
    }

    override fun setTabList(list: List<ProjectTypeBean>) {
        list.forEach {
            mFragments.add(TabListFragment())
        }
        viewPager.adapter =
            TabFragmentAdapter(mFragments, list, activity!!.supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

}