package com.zocky.githubapp.ui.detail.fragment

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(username, position == 0)
    }

    override fun getItemCount(): Int {
        return 2
    }
}
