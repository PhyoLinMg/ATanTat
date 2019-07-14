package com.elemental.atantat.viewmodel.SubjectViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elemental.atantat.repository.subjectRepo.SubjectRepository

class SubjectViewModelFactory(private val subjectRepository: SubjectRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SubjectViewModel(subjectRepository) as T
    }

}