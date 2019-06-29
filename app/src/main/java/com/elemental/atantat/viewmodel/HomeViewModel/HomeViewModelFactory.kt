package com.elemental.atantat.viewmodel.HomeViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elemental.atantat.repository.loginRepo.LoginRepository
import com.elemental.atantat.repository.periodRepo.PeriodRepository
import com.elemental.atantat.viewmodel.LoginViewModel.LoginViewModel

class HomeViewModelFactory(private val periodRepository: PeriodRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(periodRepository) as T
    }
}