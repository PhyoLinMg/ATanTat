package com.elemental.atantat.ui.Fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders

import com.elemental.atantat.R

import com.elemental.atantat.repository.signupRepo.SignUpRepositoryImpl
import com.elemental.atantat.utils.DataLoadState
import com.elemental.atantat.utils.SharedPreference

import com.elemental.atantat.viewmodel.SignUpViewModel.SignUpViewModel
import com.elemental.atantat.viewmodel.SignUpViewModel.SignUpViewModelFactory
import com.google.android.material.snackbar.Snackbar


import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.email
import kotlinx.android.synthetic.main.fragment_register.password
import kotlinx.android.synthetic.main.fragment_register.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.x.kodein

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RegisterFragment : Fragment(),KodeinAware {
    override val kodein by kodein()
    private val viewModelFactory: SignUpViewModelFactory by instance()
    private lateinit var myView: View
    private lateinit var viewModel:SignUpViewModel
    private lateinit var sharedPreference:SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_register, container, false);
        // Inflate the layout for this fragment
        return myView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedPreference= SharedPreference(context)

        determinateBar.visibility = View.INVISIBLE
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(
            SignUpViewModel::class.java)
        view!!.btn_signup.setOnClickListener {
            viewModel.signup(name.text.toString(),email.text.toString(),password.text.toString(),password_confirmation.text.toString(),activity)
        }
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
                            viewModel.signup(name.text.toString(),email.text.toString(),password.text.toString(),password_confirmation.text.toString(),activity)
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
