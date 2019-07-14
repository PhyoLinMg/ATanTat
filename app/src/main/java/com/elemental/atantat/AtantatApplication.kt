package com.elemental.atantat

import android.app.Application
import com.elemental.atantat.network.ConnectivityInterceptor
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.services.MainService
import com.elemental.atantat.network.services.PublicService
import com.elemental.atantat.network.services.UserLoginSignUpInterface
import com.elemental.atantat.repository.loginRepo.LoginRepository
import com.elemental.atantat.repository.loginRepo.LoginRepositoryImpl
import com.elemental.atantat.repository.majorRepo.MajorRepository
import com.elemental.atantat.repository.majorRepo.MajorRepositoryImpl
import com.elemental.atantat.repository.periodRepo.PeriodRepository
import com.elemental.atantat.repository.periodRepo.PeriodRepositoryImpl
import com.elemental.atantat.repository.signupRepo.SignUpRepository
import com.elemental.atantat.repository.signupRepo.SignUpRepositoryImpl
import com.elemental.atantat.repository.subjectRepo.SubjectRepository
import com.elemental.atantat.repository.subjectRepo.SubjectRepositoryImpl
import com.elemental.atantat.repository.universityRepo.UniversityRepository
import com.elemental.atantat.repository.universityRepo.UniversityRepositoryImpl
import com.elemental.atantat.viewmodel.HomeViewModel.HomeViewModelFactory
import com.elemental.atantat.viewmodel.LoginViewModel.LoginViewModelFactory
import com.elemental.atantat.viewmodel.MajorViewModel.MajorViewModelFactory
import com.elemental.atantat.viewmodel.SignUpViewModel.SignUpViewModelFactory
import com.elemental.atantat.viewmodel.SubjectViewModel.SubjectViewModelFactory
import com.elemental.atantat.viewmodel.UniversityViewModel.UniversityViewModelFactory
import com.facebook.stetho.Stetho
import me.myatminsoe.mdetect.MDetect
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class AtantatApplication: Application(),KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@AtantatApplication))

        bind<ConnectivityInterceptor>() with singleton {
            ConnectivityInterceptorImpl(instance()) }

        bind() from singleton { MainService(instance(), instance()) }
        bind() from singleton { PublicService(instance()) }
        bind() from singleton { UserLoginSignUpInterface(instance()) }

        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { LoginViewModelFactory(instance()) }
        bind() from provider {SignUpViewModelFactory(instance())}
        bind() from provider{ UniversityViewModelFactory(instance()) }
        bind() from provider{MajorViewModelFactory(instance())}
        bind() from provider {SubjectViewModelFactory(instance())}

        bind<LoginRepository>() with singleton { LoginRepositoryImpl(instance())}
        bind<SignUpRepository>() with singleton{ SignUpRepositoryImpl(instance()) }
        bind<PeriodRepository>() with singleton { PeriodRepositoryImpl(instance()) }
        bind<UniversityRepository>() with singleton { UniversityRepositoryImpl(instance()) }
        bind<MajorRepository>() with singleton { MajorRepositoryImpl(instance()) }
        bind<SubjectRepository>() with singleton { SubjectRepositoryImpl(instance()) }
    }
    override fun onCreate() {
        super.onCreate()
        MDetect.init(this)
        Stetho.initializeWithDefaults(this)

    }
}