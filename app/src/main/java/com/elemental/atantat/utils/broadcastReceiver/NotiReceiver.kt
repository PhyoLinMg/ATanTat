package com.elemental.atantat.utils.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.elemental.atantat.data.models.Times
import com.elemental.atantat.db.AtanTatDatabase
import com.elemental.atantat.db.dao.PeriodDao
import com.elemental.atantat.ui.Activity.NotiActivity
import com.elemental.atantat.utils.Calculations
import org.jetbrains.anko.doAsync
import java.util.*
import kotlin.collections.ArrayList

class NotiReceiver: BroadcastReceiver() {
    private lateinit var db:AtanTatDatabase
    private val times: MutableList<Times> = ArrayList()
    private val calculations:Calculations= Calculations()

    override fun onReceive(context: Context?, intent: Intent?) {
        val phone_hour=getHourofDay()
        val phone_min=getMinute()
        db= AtanTatDatabase.invoke(context!!)
        doAsync {
            times.addAll(db.PeriodDao().times())
            for (time in times){
                val array=calculations.convert(time.startTime!!)
                val minute=array[1].toInt()
                val hour=calculations.twentyfourhrformat(array)
                if(phone_hour==hour && phone_min==minute){
                    val bundle:Bundle=Bundle()

                    val intent=Intent(context,NotiActivity::class.java)
                    intent.putExtra("subjectId",time.subjectID)
                    startActivity(context,intent,null)
                }
            }
        }

        Toast.makeText(context,"Received",Toast.LENGTH_SHORT).show()

    }

    private fun getHourofDay(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    private fun getMinute(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MINUTE)
    }

}