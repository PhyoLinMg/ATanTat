package com.elemental.atantat.ui.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.elemental.atantat.R
import com.elemental.atantat.adapter.PeriodAdapter
import com.elemental.atantat.data.models.Period

import com.elemental.atantat.utils.DataLoadState

import com.elemental.atantat.viewmodel.HomeViewModel.HomeViewModel
import com.elemental.atantat.viewmodel.HomeViewModel.HomeViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.determinateBar

import kotlinx.android.synthetic.main.home_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment() ,KodeinAware{

    override val kodein by kodein()
    private val viewModelFactory: HomeViewModelFactory by instance()

    private lateinit var myView: View
    private lateinit var periodAdapter: PeriodAdapter
    private val periods: MutableList<Period> = ArrayList()

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
        Log.d("created","Home Created")
        viewModel = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)
        periodAdapter= PeriodAdapter(periods,context!!)
        periodcycler.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = periodAdapter
        }

        if (periods != null && periods.isEmpty()) {
            viewModel.loadPeriods()
            viewModel.getPeriods().observe(viewLifecycleOwner,Observer{
                periods.addAll(it)
                periodAdapter.notifyDataSetChanged()
            })
        }


        viewModel.getLoadState().observe(viewLifecycleOwner, Observer {
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

    override fun onPause() {
        super.onPause()
        Log.d("paused","Home Paused")
        Log.d("periods",periods.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJob()
    }


}
