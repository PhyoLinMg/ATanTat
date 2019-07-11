package com.elemental.atantat.usecases


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class Ids(val context: LifecycleOwner){
    private val ids:MutableLiveData<List<Int>> = MutableLiveData()
    private val idlist:MutableList<Int> =ArrayList()
    fun addId(id:Int){
        ids.observe(context, Observer {
            idlist.add(id)
        })
    }
    fun getids():List<Int>{
        return idlist
    }
}