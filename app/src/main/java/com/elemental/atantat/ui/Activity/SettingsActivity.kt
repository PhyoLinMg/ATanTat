package com.elemental.atantat.ui.Activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity;
import com.elemental.atantat.R
import com.elemental.atantat.utils.SharedPreference

import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.content_settings.*


class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreference: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPreference = SharedPreference(this)


        switchWidget.setOnClickListener {
            if (switchWidget.isChecked) {
                sharedPreference.save("color", Color.DKGRAY)
                layout.setBackgroundColor(Color.DKGRAY)
            } else {
                sharedPreference.save("color", Color.WHITE)
                layout.setBackgroundColor(Color.WHITE)
            }
        }
        val color=sharedPreference.getValueInt("color")
        switchWidget.isChecked = color==Color.DKGRAY
        layout.setBackgroundColor(color)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@SettingsActivity, MainActivity::class.java)
        startActivity(intent)

    }

}
