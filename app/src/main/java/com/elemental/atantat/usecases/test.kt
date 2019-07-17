package com.elemental.atantat.usecases

import android.content.Context
import com.elemental.atantat.data.models.Subject
import com.elemental.atantat.db.AtanTatDatabase

class test(val context: Context) {
    private val db:AtanTatDatabase= AtanTatDatabase.invoke(context)
    fun test(id:Int):List<Subject>{
        return db.SubjectDao().subject(id)
    }
}