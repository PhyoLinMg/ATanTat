package com.elemental.atantat.ui.Activity

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar
import com.elemental.atantat.R


class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
