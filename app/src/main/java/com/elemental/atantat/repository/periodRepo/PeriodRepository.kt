package com.elemental.atantat.repository.periodRepo

import androidx.lifecycle.LiveData
import com.elemental.atantat.data.models.Period
import com.elemental.atantat.utils.DataLoadState
import kotlinx.coroutines.CoroutineScope

interface PeriodRepository :CoroutineScope{
    fun getDataLoadState(): LiveData<DataLoadState>
    fun loadPeriod()
    fun getPeriod():LiveData<List<Period>>
    fun cancelJob()
}