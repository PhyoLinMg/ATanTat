package com.elemental.atantat.ui.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elemental.atantat.R
import com.elemental.atantat.db.AtanTatDatabase
import com.elemental.atantat.utils.SharedPreference
import com.elemental.atantat.viewmodel.ProfileViewModel.ProfileViewModel
import com.elemental.atantat.viewmodel.ProfileViewModel.ProfileViewModelFactory
import kotlinx.android.synthetic.main.content_profile.*
import org.jetbrains.anko.doAsync
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

import org.kodein.di.generic.instance

class ProfileActivity : AppCompatActivity(), KodeinAware{

    override val kodein by kodein()
    private lateinit var profileViewModel:ProfileViewModel
    private lateinit var sharedPreference: SharedPreference
    private val db=AtanTatDatabase(this)


    private val profileViewModelFactory:ProfileViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        profileViewModel = ViewModelProvider(this, profileViewModelFactory).get(
            ProfileViewModel::class.java)

        profileViewModel.loadUser()


        profileViewModel.getUser().observe(this, Observer {
            profile_name.text=profileViewModel.getUser().value!!.name
            profile_email.text=profileViewModel.getUser().value!!.email
            profile_major.text=profileViewModel.getUser().value!!.major
            profile_uni.text=profileViewModel.getUser().value!!.university
            Log.d("profile",profileViewModel.getUser().value.toString())
        })
        sharedPreference= SharedPreference(this)
        changecolor()
        logout.setOnClickListener {
            sharedPreference.clearSharedPreference()
            doAsync {
                db.SubjectDao().deleteTable()
                db.PeriodDao().deleteTable()
                db.MajorDao().deleteTable()
            }
            val intent= Intent(this,LoginRegisterActivity::class.java)
            startActivity(intent)
        }

    }
    private fun changecolor() {
        val color = sharedPreference.getValueString("color")
        if(color=="black"){
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
        }
        else{
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        profileViewModel.cancelJob()
    }

}
