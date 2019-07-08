package com.elemental.atantat.repository.majorRepo

import androidx.lifecycle.LiveData
import com.elemental.atantat.data.models.Major
import com.elemental.atantat.utils.DataLoadState
import kotlinx.coroutines.CoroutineScope

interface MajorRepository :CoroutineScope{

    fun getDataLoadState(): LiveData<DataLoadState>
    fun loadMajors()
    fun getMajors(): LiveData<List<Major>>
    fun cancelJob()
}