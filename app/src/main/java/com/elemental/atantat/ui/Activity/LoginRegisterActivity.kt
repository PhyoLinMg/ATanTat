package com.elemental.atantat.ui.Activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import com.elemental.atantat.R
import com.elemental.atantat.ui.Fragment.LoginFragment
import com.elemental.atantat.ui.Fragment.RegisterFragment
import kotlinx.android.synthetic.main.activity_login_register.view.*

class LoginRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)
        val btnlogin=findViewById<Button>(R.id.login)
        val btnsignup=findViewById<Button>(R.id.signup)
        val manager : FragmentManager = supportFragmentManager
        var fragment : Fragment? = manager.findFragmentById(R.id.ReplaceFrame)

       if(fragment==null){
           fragment=LoginFragment()
           supportFragmentManager.beginTransaction().replace(R.id.ReplaceFrame,fragment).commit()
       }
        btnsignup.setOnClickListener {
            var signupfragment=RegisterFragment()
            supportFragmentManager.beginTransaction().replace(R.id.ReplaceFrame,signupfragment).commit()

        }
        btnlogin.setOnClickListener {
            var loginfragment=LoginFragment()
            supportFragmentManager.beginTransaction().replace(R.id.ReplaceFrame,loginfragment).commit()

        }


    }
}
