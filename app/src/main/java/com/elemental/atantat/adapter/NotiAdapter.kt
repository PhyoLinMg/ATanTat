package com.elemental.atantat.adapter

import android.text.Html
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elemental.atantat.R
import com.elemental.atantat.data.models.Subject
import com.elemental.atantat.utils.Calculations
import com.elemental.atantat.utils.inflate
import kotlinx.android.synthetic.main.noti_card.view.*


class NotiAdapter(val subjects:List<Subject>, private val listener:OnItemClickedListener): RecyclerView.Adapter<NotiAdapter.NotiViewHolder>() {
    private val calculations=Calculations()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiViewHolder {
        return NotiViewHolder(parent.inflate(R.layout.noti_card))
    }
    interface OnItemClickedListener {
        fun onItemClicked(subject: Subject)
    }

    override fun getItemCount(): Int =subjects.size

    override fun onBindViewHolder(holder: NotiViewHolder, position: Int) {
        val subject=subjects[position]
        holder.bind(subject,listener)
    }

    inner class NotiViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private var percentage:Int=0
        private val sub_name=itemView.sub_name
        private val description=itemView.noti
        fun bind(subject: Subject,listener:OnItemClickedListener){
            itemView.setOnClickListener{
                listener.onItemClicked(subject)
            }
            percentage=calculations.calculatePercentage(subject.yes,subject.no)
            sub_name.text=subject.name
            if(percentage<75) {
                val first="You need "
                val second="<font color='#EE0000'>${75-percentage}</font>"
                val third="% to get 75%"
                description.text=Html.fromHtml(first+second+third)
            } else
                description.text="You already get 75%.ENJOY!!!"

        }

    }
}