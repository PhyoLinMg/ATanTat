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
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.elemental.atantat.NotificationActivity
import com.elemental.atantat.data.models.YesNo
import com.elemental.atantat.db.AtanTatDatabase
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.services.MainService
import com.elemental.atantat.usecases.PostAttendanceUseCase
import com.elemental.atantat.utils.DataLoadState
import com.elemental.atantat.utils.NetworkUtil
import com.elemental.atantat.utils.broadcastReceiver.InternetReceiver
import org.jetbrains.anko.doAsync


class MainActivity : AppCompatActivity() {
    var fragment: Fragment? = null
    private lateinit var sharedPreference: SharedPreference
    private lateinit var MyReceiver: BroadcastReceiver
    private lateinit var db:AtanTatDatabase
    private lateinit var  postAttendanceUseCase:PostAttendanceUseCase
    private lateinit var api:MainService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        sharedPreference= SharedPreference(this)

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
        adapter.addFragment(MajorFragment(), "Graph")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    private fun changecolor() {
        val color = sharedPreference.getValueString("color")
        if(color=="black"){
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
            setUpViewPager()
        }
        else{
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
            setUpViewPager()
        }

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
        R.id.notification ->{
            val notification=Intent(this@MainActivity,NotificationActivity::class.java)
            startActivity(notification)
            true
        }
        R.id.synchronise->{
            val networkUtil= NetworkUtil()
            var status:String?=networkUtil.getConnectivityStatusString(this)
            if(status!!.isEmpty()){
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
            else if(status=="Mobile data enabled" || status=="Wifi enabled"){
                db= AtanTatDatabase.invoke(this)
                api= MainService.invoke(ConnectivityInterceptorImpl(this),sharedPreference.getValueString("token")!!)
                postAttendanceUseCase= PostAttendanceUseCase(this,sharedPreference)
                doAsync {
                    val yesNo=db.SubjectDao().getattendence()
                    yesNo.forEach {
                        val yesno=YesNo(it.id,it.yes,it.no)
                        postAttendanceUseCase.go(yesno)
                    }
                }
                Toast.makeText(this,"Successful",Toast.LENGTH_SHORT).show()
            }

            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }

    }


}
