package com.elemental.atantat.repository.universityRepo

import androidx.lifecycle.LiveData
import com.elemental.atantat.data.models.University
import com.elemental.atantat.utils.DataLoadState

interface UniversityRepository {
    fun getDataLoadState(): LiveData<DataLoadState>
    fun loadUniversities()
    fun getUniversities(): LiveData<List<University>>
    fun cancelJob()
}