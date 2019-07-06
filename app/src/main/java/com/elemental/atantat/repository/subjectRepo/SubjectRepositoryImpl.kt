package com.elemental.atantat.repository.subjectRepo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.atantat.data.models.Subject
import com.elemental.atantat.db.AtanTatDatabase
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.services.MainService
import com.elemental.atantat.utils.DataLoadState
import com.elemental.atantat.utils.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class SubjectRepositoryImpl(val context:Context) : SubjectRepository,CoroutineScope{
    private val mJob: Job = Job()
    private val dataLoadState: MutableLiveData<DataLoadState> = MutableLiveData()
    private val subjects: MutableLiveData<List<Subject>> = MutableLiveData()
    private val sharedPreference: SharedPreference = SharedPreference(context)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + mJob

    private val api: MainService = MainService.invoke(ConnectivityInterceptorImpl(context),sharedPreference.getValueString("token")!!)
    private val db: AtanTatDatabase = AtanTatDatabase.invoke(context)

    override fun getDataLoadState(): LiveData<DataLoadState> {
        return dataLoadState
    }

    override fun loadSubjects() {

    }

    override fun getSubjects(): LiveData<List<Subject>> {
        return subjects
    }

    override fun cancelJob() {

    }
}