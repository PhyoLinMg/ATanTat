package com.elemental.atantat.ui.Activity

import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.elemental.atantat.R
import com.elemental.atantat.viewmodel.ProfileViewModel.ProfileViewModel
import com.elemental.atantat.viewmodel.ProfileViewModel.ProfileViewModelFactory
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

import org.kodein.di.generic.instance






class ProfileActivity : AppCompatActivity(), KodeinAware{

    override val kodein by kodein()
    private lateinit var profileViewModel:ProfileViewModel
    private val profileViewModelFactory:ProfileViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        profileViewModel = ViewModelProviders.of(this, profileViewModelFactory).get(
            ProfileViewModel::class.java)
        profileViewModel.loadUser()

        profileViewModel.getUser().observe(this, Observer {
            profile_name.text=profileViewModel.getUser().value!!.name
            header_name.text=profileViewModel.getUser().value!!.name
            profile_email.text=profileViewModel.getUser().value!!.email
            header_email.text=profileViewModel.getUser().value!!.email
            profile_major.text=profileViewModel.getUser().value!!.major
            profile_uni.text=profileViewModel.getUser().value!!.university
            Log.d("profile",profileViewModel.getUser().value.toString())
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        profileViewModel.cancelJob()
    }

}
