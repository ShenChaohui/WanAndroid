package com.sch.wanandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sch.wanandroid.entity.TabTypeBean

/**
 * Created by Sch.
 * Date: 2020/4/21
 * description: 项目/公众号 子fragment Adapter
 */
class TabFragmentAdapter(
    private var fragments: MutableList<Fragment>,
    private var tabTypeBeans: MutableList<TabTypeBean>,
    fm: FragmentManager
) :
    FragmentStatePagerAdapter(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTypeBeans.get(position).name
    }
    fun updata(fragments: MutableList<Fragment>){
        this.fragments = fragments
        notifyDataSetChanged()
    }
}