package com.elemental.atantat.ui.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elemental.atantat.R
import kotlinx.android.synthetic.main.activity_noti.*

class NotiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noti)

        btnyes.setOnClickListener{
            finish()
        }
        btn_no.setOnClickListener{

        }
        btncancel.setOnClickListener{}

    }
}
