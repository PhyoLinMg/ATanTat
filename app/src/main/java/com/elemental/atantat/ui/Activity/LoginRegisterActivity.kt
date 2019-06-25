package com.elemental.atantat.ui.Activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import com.elemental.atantat.R
import com.elemental.atantat.ui.Fragment.LoginFragment
import com.elemental.atantat.ui.Fragment.RegisterFragment
import com.elemental.atantat.utils.SharedPreference
import kotlinx.android.synthetic.main.activity_login_register.*


class LoginRegisterActivity : AppCompatActivity() {
    private lateinit var sharedPreference:SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        val btnlogin=findViewById<Button>(R.id.login)
        val btnsignup=findViewById<Button>(R.id.signup)

        sharedPreference= SharedPreference(this)
        layout.setBackgroundColor(sharedPreference.getValueInt("color"))


        val manager : FragmentManager = supportFragmentManager
        var fragment : Fragment? = manager.findFragmentById(R.id.ReplaceFrame)

        if(fragment==null){
            fragment=LoginFragment()
            supportFragmentManager.beginTransaction().replace(R.id.ReplaceFrame,fragment).commit()
        }
        btnsignup.setOnClickListener {
            val signupfragment=RegisterFragment()
            supportFragmentManager.beginTransaction().replace(R.id.ReplaceFrame,signupfragment).commit()

        }
        btnlogin.setOnClickListener {
            val loginfragment=LoginFragment()
            supportFragmentManager.beginTransaction().replace(R.id.ReplaceFrame,loginfragment).commit()

        }


    }
}