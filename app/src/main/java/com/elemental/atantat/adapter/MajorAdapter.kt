package com.elemental.atantat.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elemental.atantat.R
import com.elemental.atantat.data.models.Major
import com.elemental.atantat.utils.inflate
import kotlinx.android.synthetic.main.major_card.view.*

class MajorAdapter(private val majors:List<Major>) : RecyclerView.Adapter<MajorAdapter.MajorViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MajorViewHolder {
        return MajorViewHolder(parent.inflate(R.layout.major_card))
    }

    override fun getItemCount(): Int =majors.size

    override fun onBindViewHolder(holder: MajorViewHolder, position: Int) {
        val major=majors[position]
        holder.bind(major)
    }


    inner class MajorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val majorName=itemView.major_name!!
        fun bind(major: Major) {
            majorName.text=major.name
        }
    }
}