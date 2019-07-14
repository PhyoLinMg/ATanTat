package com.elemental.atantat.ui.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.elemental.atantat.R
import com.elemental.atantat.data.models.Period
import com.elemental.atantat.repository.periodRepo.PeriodRepository
import com.elemental.atantat.repository.periodRepo.PeriodRepositoryImpl
import com.elemental.atantat.utils.DataLoadState
import com.elemental.atantat.utils.SharedPreference
import com.elemental.atantat.viewmodel.HomeViewModel.HomeViewModel
import com.elemental.atantat.viewmodel.HomeViewModel.HomeViewModelFactory
import com.elemental.atantat.viewmodel.LoginViewModel.LoginViewModel
import com.elemental.atantat.viewmodel.LoginViewModel.LoginViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.determinateBar
import kotlinx.android.synthetic.main.fragment_login.email
import kotlinx.android.synthetic.main.fragment_login.password
import kotlinx.android.synthetic.main.fragment_register.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment() ,KodeinAware{

    override val kodein by kodein()
    private val viewModelFactory: HomeViewModelFactory by instance()

    private lateinit var myView: View
    private val periods: MutableList<Period> = ArrayList()

    private lateinit var sharedPreference: SharedPreference

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView= inflater.inflate(R.layout.home_fragment, container, false)
        return myView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(HomeViewModel::class.java)


        viewModel.loadPeriods()

//        viewModel.getPeriods().observe(this, Observer {
//            periods.addAll(it)
//        })

        viewModel.getLoadState().observe(this, Observer {
            when(it) {
                DataLoadState.LOADING -> {
                    determinateBar.visibility = View.VISIBLE
                }
                DataLoadState.LOADED -> {
                    determinateBar.visibility = View.GONE
                }
                DataLoadState.FAILED -> {
                    determinateBar.visibility = View.VISIBLE
                    Snackbar.make(myView, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                        .setAction("RETRY") {

                        }.show()
                }
            }
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJob()
    }


}
