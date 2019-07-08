package com.elemental.atantat.viewmodel.UniversityViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.elemental.atantat.data.models.University
import com.elemental.atantat.repository.universityRepo.UniversityRepository
import com.elemental.atantat.utils.DataLoadState

class UniversityViewModel(private val universityRepository: UniversityRepository): ViewModel() {
    fun getDataLoadState(): LiveData<DataLoadState>{
        return universityRepository.getDataLoadState()
    }
    fun loadUniversities(){
        return universityRepository.loadUniversities()
    }
    fun getUniversities():LiveData<List<University>>{
        return universityRepository.getUniversities()
    }
    fun cancelJob(){
        return universityRepository.cancelJob()
    }
}