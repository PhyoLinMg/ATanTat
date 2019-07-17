package com.elemental.atantat.adapter


import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elemental.atantat.R
import com.elemental.atantat.data.models.Subject
import com.elemental.atantat.utils.Calculations
import com.elemental.atantat.utils.inflate


class SubjectAdapter(val subjects:List<Subject>): RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {
    private val calculations:Calculations= Calculations()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        return SubjectViewHolder(parent.inflate(R.layout.subject_card))
    }

    override fun getItemCount()=subjects.size

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject=subjects[position]
        holder.bind(subject)
    }

    inner class SubjectViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name=itemView.findViewById<TextView>(R.id.name)

        val txtpercentage=itemView.findViewById<TextView>(R.id.txt_percentage)

        fun bind(subject:Subject){
            val percentage=calculations.calculatePercentage(subject.yes,subject.no)
            name.text=subject.name
            txtpercentage.text=percentage.toString()+"%"
            if(percentage>=75)
                txtpercentage.setTextColor(Color.GREEN)
            else if(percentage<75 &&  percentage>=50)
                txtpercentage.setTextColor(Color.YELLOW)
            else
                txtpercentage.setTextColor(Color.RED)


        }
    }

}