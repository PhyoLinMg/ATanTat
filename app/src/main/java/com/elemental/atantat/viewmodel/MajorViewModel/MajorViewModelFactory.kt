package com.elemental.atantat.viewmodel.MajorViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elemental.atantat.repository.majorRepo.MajorRepository

class MajorViewModelFactory(private val majorRepository: MajorRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MajorViewModel(majorRepository) as T
    }
}