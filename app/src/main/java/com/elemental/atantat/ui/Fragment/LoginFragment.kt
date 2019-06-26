package com.elemental.atantat.ui.Fragment


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders

import com.elemental.atantat.R
import com.elemental.atantat.utils.DataLoadState
import com.elemental.atantat.utils.SharedPreference


import com.elemental.atantat.viewmodel.LoginViewModel.LoginViewModel
import com.elemental.atantat.viewmodel.LoginViewModel.LoginViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*


import kotlinx.android.synthetic.main.fragment_login.view.*

import kotlinx.android.synthetic.main.fragment_register.email
import kotlinx.android.synthetic.main.fragment_register.layout
import kotlinx.android.synthetic.main.fragment_register.password

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
class LoginFragment : Fragment(),KodeinAware {
    override val kodein by kodein()
    private val viewModelFactory: LoginViewModelFactory by instance()
    private lateinit var myView: View
    private lateinit var viewModel:LoginViewModel
    private lateinit var sharedPreference: SharedPreference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView= inflater.inflate(R.layout.fragment_login, container, false)
        return myView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreference= SharedPreference(context)
        determinateBar.visibility = View.INVISIBLE
        layout.setBackgroundColor(sharedPreference.getValueInt("color"))
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(
            LoginViewModel::class.java)
        view!!.btn_login.setOnClickListener {

            //Log.d("email",password.text.toString())
            viewModel.login(email.text.toString(),password.text.toString(),activity)
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
                            viewModel.login(email.text.toString(),password.text.toString(),activity)
                        }.show()
                }
            }
        })
    }



}
