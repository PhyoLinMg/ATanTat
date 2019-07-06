package com.elemental.atantat.repository.subjectRepo

import androidx.lifecycle.LiveData
import com.elemental.atantat.data.models.Subject
import com.elemental.atantat.utils.DataLoadState

interface SubjectRepository {
    fun getDataLoadState(): LiveData<DataLoadState>
    fun loadSubjects()
    fun getSubjects(): LiveData<List<Subject>>
    fun cancelJob()
}