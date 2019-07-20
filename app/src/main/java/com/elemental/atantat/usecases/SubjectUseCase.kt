package com.elemental.atantat.usecases

import android.content.Context
import com.elemental.atantat.data.models.Subject
import com.elemental.atantat.db.AtanTatDatabase

class SubjectUseCase(val context: Context){

    private val db:AtanTatDatabase= AtanTatDatabase.invoke(context)
    fun getsubject(id:Int):List<Subject>{
        return db.SubjectDao().subject(id)
    }
}