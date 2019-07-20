package com.elemental.atantat.repository.subjectRepo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.atantat.data.models.Subject
import com.elemental.atantat.db.AtanTatDatabase
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.NoConnectivityException
import com.elemental.atantat.network.services.MainService
import com.elemental.atantat.utils.DataLoadState
import com.elemental.atantat.utils.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
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
        dataLoadState.postValue(DataLoadState.LOADING)
        launch {
            try {
                val response=api.getSubjectsAsync().await()
                when {
                    response.isSuccessful ->  {
                        if(db.SubjectDao().subjects().count()==0){
                            db.SubjectDao().insert(response.body()!!.subjects)
                        }
                    }
                }
            } catch (e: NoConnectivityException) {
                Log.e("MY_ERROR", "No Internet Connection But You can use offline")
                dataLoadState.postValue(DataLoadState.FAILED)
            } catch (e: Throwable) {
                Log.e("MY_ERROR", "I don't know! $e")
                dataLoadState.postValue(DataLoadState.FAILED)
            }
            subjects.postValue(db.SubjectDao().subjects())
            dataLoadState.postValue(DataLoadState.LOADED)
        }
    }

    override fun getSubjects(): LiveData<List<Subject>> {
        dataLoadState.postValue(DataLoadState.LOADED)
        return subjects
    }

    override fun cancelJob() {
        mJob.cancel()
    }
    private fun load(){
        subjects.postValue(db.SubjectDao().subjects())
        dataLoadState.postValue(DataLoadState.LOADED)
    }
}