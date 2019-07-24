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
import android.widget.Toast

import android.app.PendingIntent

import android.app.AlarmManager

import android.content.Context
import com.elemental.atantat.utils.broadcastReceiver.NotiReceiver






class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreference: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPreference = SharedPreference(this)

        backgroundChange()
        notification()
    }

    private fun backgroundChange(){
        switchWidget.setOnClickListener {
            if (switchWidget.isChecked) {
                sharedPreference.save("color", Color.DKGRAY)
                text.setTextColor(Color.WHITE)
                layout.setBackgroundColor(Color.DKGRAY)
            } else {
                sharedPreference.save("color", Color.WHITE)
                text.setTextColor(Color.BLACK)
                layout.setBackgroundColor(Color.WHITE)
            }
        }
        val color=sharedPreference.getValueInt("color")
        if(color==Color.DKGRAY){
            switchWidget.isChecked =true
            text.setTextColor(Color.WHITE)
        }
        else{
            switchWidget.isChecked=false
            text.setTextColor(Color.BLACK)
        }
        layout.setBackgroundColor(color)
    }
    private fun notification(){
        switchNotification.setOnClickListener{
            if(switchNotification.isChecked){
                sharedPreference.save("noti",true)
            }
            else{
                sharedPreference.save("noti",false)
            }
            val status=sharedPreference.getValueBoolean("noti",false)
            if(status){
                val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(this, NotiReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60000,
                    pendingIntent
                )
                Toast.makeText(this, "Notification on", Toast.LENGTH_SHORT).show()


            }

        }
    }



}
