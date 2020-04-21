package com.sch.playandroid.ui.main.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sch.lolcosmos.base.BaseFragment
import com.sch.playandroid.R
import com.sch.playandroid.adapter.TabFragmentAdapter
import com.sch.playandroid.entity.TabTypeBean
import com.sch.playandroid.ui.main.tab.list.TabListFragment
import kotlinx.android.synthetic.main.fragment_tab.*

class TabFragment : BaseFragment(), TabContract.ITabView {
    private val presenter by lazy { TabPresenterImpl(this) }
    private val mFragments by lazy { ArrayList<Fragment>() }
    private val tabList by lazy { ArrayList<TabTypeBean>() }

    var type: Int? = null
    override fun init(savedInstanceState: Bundle?) {
        type = arguments?.getInt("type")!!
        type?.let {
            presenter.getTabList(it)
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_tab
    }

    override fun setTabList(list: List<TabTypeBean>) {
        tabList.clear()
        tabList.addAll(list)
        initListFragment()

    }

    private fun initListFragment() {
        mFragments.clear()
        tabList.forEach {
            val tabListFragment = TabListFragment()
            val bundle = Bundle()
            bundle.putInt("id", it.id)
            type?.let { it1 -> bundle.putInt("type", it1) }
            bundle.putString("name", it.name)
            tabListFragment.arguments = bundle
            mFragments.add(tabListFragment)
        }
        viewPager.adapter =
            TabFragmentAdapter(mFragments, tabList, activity!!.supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

}