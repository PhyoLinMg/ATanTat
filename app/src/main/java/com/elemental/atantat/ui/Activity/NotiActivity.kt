package com.elemental.atantat.ui.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.elemental.atantat.R
import com.elemental.atantat.usecases.AtanTatUseCase
import kotlinx.android.synthetic.main.activity_noti.*
import org.jetbrains.anko.doAsync

class NotiActivity : AppCompatActivity() {
    private val atanTatUseCase:AtanTatUseCase= AtanTatUseCase(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noti)
        val string:String= intent.getStringExtra("subjectId")
        Log.d("string",string)
        val subjectId=string.toInt()
        btnyes.setOnClickListener{
            doAsync {
                atanTatUseCase.add(subjectId,true)
            }
            finish()
        }
        btn_no.setOnClickListener{
            doAsync {
                atanTatUseCase.add(subjectId,false)
            }
            finish()
        }
        btncancel.setOnClickListener{
        }

    }
}
