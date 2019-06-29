package com.elemental.atantat.viewmodel.HomeViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.elemental.atantat.data.models.Period
import com.elemental.atantat.repository.periodRepo.PeriodRepository
import com.elemental.atantat.utils.DataLoadState

class HomeViewModel(val periodRepository: PeriodRepository) : ViewModel() {

    // TODO: Implement the ViewModel
    fun loadPeriods(){
        return periodRepository.loadPeriod()
    }
    fun getPeriods(): LiveData<List<Period>>{
        return periodRepository.getPeriod()
    }


    fun getLoadState(): LiveData<DataLoadState> {
        return periodRepository.getDataLoadState()
    }
    fun cancelJob(){
        periodRepository.cancelJob()
    }

}
