package com.elemental.atantat.adapter

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.elemental.atantat.R
import com.elemental.atantat.data.models.Period
import com.elemental.atantat.data.models.Subject
import com.elemental.atantat.db.AtanTatDatabase
import com.elemental.atantat.usecases.test
import com.elemental.atantat.utils.inflate
import kotlinx.android.synthetic.main.period_card.view.*
import org.jetbrains.anko.doAsync

class PeriodAdapter (private val periods: List<Period>, val context: Context): RecyclerView.Adapter<PeriodAdapter.PeriodViewHolder>() {
    private val db:AtanTatDatabase= AtanTatDatabase.invoke(context)
    private val test:test= test(context)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeriodViewHolder {
        return PeriodViewHolder(parent.inflate(R.layout.period_card))
    }

    override fun getItemCount()=periods.size

    override fun onBindViewHolder(holder: PeriodViewHolder, position: Int) {
        val period=periods[position]
        holder.bind(period)
    }

    inner class PeriodViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val btnYes=itemView.findViewById<Button>(R.id.btnyes)
        val btnNo=itemView.findViewById<Button>(R.id.btnno)
        val periodName=itemView.findViewById<TextView>(R.id.periodName)
        val start=itemView.findViewById<TextView>(R.id.start)
        val end=itemView.findViewById<TextView>(R.id.end)

        override fun onClick(view: View?) {
            if(view?.id==btnYes.id){
               doAsync {
                   Log.d("yes",test.test(periods[adapterPosition].subjectId).toString())
               }
            }
            else if(view?.id==btnNo.id){
                doAsync {
                    Toast.makeText(context,test.test(periods[adapterPosition].subjectId).toString(),Toast.LENGTH_SHORT).show()
                }

            }
        }


        fun bind(period: Period){
            periodName.text=period.subjectName
            start.text=period.startTime
            end.text=period.endTime
            btnYes.setOnClickListener(this)
            btnNo.setOnClickListener(this)
        }


    }

}