package com.elemental.atantat.viewmodel.ColorChangerViewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elemental.atantat.utils.SharedPreference

class ColorChangeViewModel(val context:Context):ViewModel() {
    private var sharedPreference:SharedPreference = SharedPreference(context)
    val colorResource = MutableLiveData<Int>()
    init {
        colorResource.value = sharedPreference.getValueInt("color")
    }
}