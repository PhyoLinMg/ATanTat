package com.elemental.atantat.usecases

import android.content.Context
import com.elemental.atantat.data.models.Subject
import com.elemental.atantat.db.AtanTatDatabase

class AtanTatUseCase (val context: Context){
    private val db:AtanTatDatabase= AtanTatDatabase.invoke(context)
    private lateinit var newsubject:Subject

    fun add(subId:Int,b:Boolean){
        val subject:List<Subject> =SubjectUseCase(context).getsubject(subId)
        newsubject = if(b) Subject(subject[0].id,subject[0].name,subject[0].yes+1,subject[0].no)
        else
            Subject(subject[0].id,subject[0].name,subject[0].yes,subject[0].no+1)

        db.SubjectDao().update(newsubject)
    }
}