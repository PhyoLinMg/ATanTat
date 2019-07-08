package com.elemental.atantat.viewmodel.UniversityViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elemental.atantat.repository.universityRepo.UniversityRepository
import com.elemental.atantat.viewmodel.SignUpViewModel.SignUpViewModel

class UniversityViewModelFactory(private val universityRepository: UniversityRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UniversityViewModel(universityRepository) as T
    }
}