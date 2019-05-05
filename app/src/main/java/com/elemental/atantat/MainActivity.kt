package com.elemental.atantat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elemental.atantat.adapter.ViewPagerAdapter
import com.elemental.atantat.ui.HomeFragment
import com.elemental.atantat.ui.MajorFragment
import com.elemental.atantat.ui.SubjectFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setUpViewPager()


    }
    private fun setUpViewPager(){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(), "Home")
        adapter.addFragment(SubjectFragment(), "Subjects")
        adapter.addFragment(MajorFragment(), "Majors")
        viewPager.adapter = adapter

        tabs.setupWithViewPager(viewPager)
    }


}
