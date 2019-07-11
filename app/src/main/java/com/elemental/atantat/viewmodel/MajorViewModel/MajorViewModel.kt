package com.elemental.atantat.viewmodel.MajorViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.elemental.atantat.data.models.Major
import com.elemental.atantat.repository.majorRepo.MajorRepository
import com.elemental.atantat.utils.DataLoadState

class MajorViewModel(val majorRepository: MajorRepository) : ViewModel() {
    fun getDataLoadState(): LiveData<DataLoadState>{
        return majorRepository.getDataLoadState()
    }
    fun loadMajors(){
        return majorRepository.loadMajors()
    }
    fun getMajors(): LiveData<List<Major>>{
        return majorRepository.getMajors()
    }

    fun cancelJob(){
        return majorRepository.cancelJob()
    }
}
