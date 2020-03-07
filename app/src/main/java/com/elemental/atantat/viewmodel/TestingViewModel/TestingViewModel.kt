package com.elemental.atantat.viewmodel.TestingViewModel

import android.content.Context
import androidx.lifecycle.ViewModel

class TestingViewModel(val context:Context): ViewModel() {
    fun returnText():String{
        return "Hello from Testing ViewModel"
    }
}