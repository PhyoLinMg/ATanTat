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
import com.elemental.atantat.utils.SharedPreference

import kotlinx.android.synthetic.main.activity_main.*
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.elemental.atantat.utils.broadcastReceiver.InternetReceiver


class MainActivity : AppCompatActivity() {
    var fragment: Fragment? = null
    private lateinit var sharedPreference: SharedPreference
    private lateinit var MyReceiver: BroadcastReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        sharedPreference = SharedPreference(this)
        MyReceiver= InternetReceiver(this)
        changecolor()
        broadcastIntent()
    }

    private fun broadcastIntent() {
        registerReceiver(MyReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onResume() {
        super.onResume()
        changecolor()
        broadcastIntent()
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(MyReceiver)
    }

    private fun setUpViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(), "Home")
        adapter.addFragment(SubjectFragment(), "Subjects")
        adapter.addFragment(MajorFragment(), "Majors")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    private fun changecolor() {
        val color = sharedPreference.getValueInt("color")
        viewPager.setBackgroundColor(color)
        setUpViewPager()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_profile -> {
            val profile = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(profile)
            true
        }
        R.id.action_setting -> {
            val setting = Intent(this@MainActivity, SettingsActivity::class.java)
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
