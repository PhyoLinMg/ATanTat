package com.elemental.atantat.ui.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment

import com.elemental.atantat.R
import com.elemental.atantat.adapter.ViewPagerAdapter
import com.elemental.atantat.ui.Fragment.HomeFragment
import com.elemental.atantat.ui.Fragment.MajorFragment
import com.elemental.atantat.ui.Fragment.SubjectFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var fragment :Fragment?=null

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_profile -> {
            var profile = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(profile)
            true
        }
        R.id.action_setting -> {
            var setting=Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(setting)
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }

    }



}
