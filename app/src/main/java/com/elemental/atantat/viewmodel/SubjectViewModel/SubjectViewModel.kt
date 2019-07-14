package com.elemental.atantat.viewmodel.SubjectViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.elemental.atantat.data.models.Subject
import com.elemental.atantat.repository.subjectRepo.SubjectRepository
import com.elemental.atantat.utils.DataLoadState

class SubjectViewModel(private val subjectRepository: SubjectRepository) : ViewModel() {

    fun getDataLoadState(): LiveData<DataLoadState>{
        return subjectRepository.getDataLoadState()
    }
    fun loadSubjects(){
        return subjectRepository.loadSubjects()
    }
    fun getSubjects(): LiveData<List<Subject>>{
        return  subjectRepository.getSubjects()
    }
    fun cancelJob(){
        return subjectRepository.cancelJob()
    }

}
