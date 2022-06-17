package com.example.kotlinbasics

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val tab_toolbar = findViewById<Toolbar>(R.id.toolbar)
        val tab_viewpager = findViewById<ViewPager>(R.id.tab_viewpager)
        val tab_tablayout = findViewById<TabLayout>(R.id.tab_tablayout)
        setSupportActionBar(tab_toolbar)
        setupViewPager(tab_viewpager)
        tab_tablayout.setupWithViewPager(tab_viewpager)
    }

    private fun setupViewPager(viewpager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(AllContests(), "All")
        adapter.addFragment(Codeforces(), "CodeForces")
        adapter.addFragment(Codechef(), "CodeChef")
        adapter.addFragment(Leetcode(), "LeetCode")
        adapter.addFragment(AtCoder(), "AtCoder")
        adapter.addFragment(TopCoder(), "TopCoder")
        adapter.addFragment(HackerEarth(), "HackerEarth")
        adapter.addFragment(HackerRank(), "HackerRank")
        adapter.addFragment(KickStart(), "Kick Start")


        viewpager.adapter = adapter
    }

    class ViewPagerAdapter (supportFragmentManager: FragmentManager) : FragmentPagerAdapter(supportFragmentManager) {
        private var fragmentList1: ArrayList<Fragment> = ArrayList()
        private var fragmentTitleList1: ArrayList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList1.get(position)
        }

        @Nullable
        override fun getPageTitle(position: Int): CharSequence {
            return fragmentTitleList1.get(position)
        }

        override fun getCount(): Int {
            return fragmentList1.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList1.add(fragment)
            fragmentTitleList1.add(title)
        }
    }
}