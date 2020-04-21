package com.sch.playandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sch.playandroid.entity.ProjectTypeBean

/**
 * Created by Sch.
 * Date: 2020/4/21
 * description:
 */
class TabFragmentAdapter(
    var fragments: List<Fragment>,
    var projectTypeBeans: List<ProjectTypeBean>,
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
        return projectTypeBeans.get(position).name
    }
}