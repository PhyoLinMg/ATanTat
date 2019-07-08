package com.elemental.atantat.ui.Fragment


import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders

import com.elemental.atantat.R
import com.elemental.atantat.data.models.University
import com.elemental.atantat.repository.signupRepo.SignUpRepository
import com.elemental.atantat.repository.signupRepo.SignUpRepositoryImpl
import com.elemental.atantat.repository.universityRepo.UniversityRepositoryImpl
import com.elemental.atantat.utils.DataLoadState
import com.elemental.atantat.utils.SharedPreference


import com.elemental.atantat.viewmodel.LoginViewModel.LoginViewModel
import com.elemental.atantat.viewmodel.LoginViewModel.LoginViewModelFactory
import com.elemental.atantat.viewmodel.MajorViewModel.MajorViewModel
import com.elemental.atantat.viewmodel.SignUpViewModel.SignUpViewModel
import com.elemental.atantat.viewmodel.SignUpViewModel.SignUpViewModelFactory
import com.elemental.atantat.viewmodel.UniversityViewModel.UniversityViewModel
import com.elemental.atantat.viewmodel.UniversityViewModel.UniversityViewModelFactory
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
class LoginFragment : Fragment(),KodeinAware,AdapterView.OnItemSelectedListener {


    override val kodein by kodein()
    private val loginviewModelFactory: LoginViewModelFactory by instance()
    private val universityViewModelFactory:UniversityViewModelFactory by instance()
    private lateinit var myView: View
    private lateinit var loginViewModel:LoginViewModel
    private lateinit var universityViewModel:UniversityViewModel
    private lateinit var sharedPreference: SharedPreference
    private val universities: MutableList<University> = ArrayList()

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
        uni!!.onItemSelectedListener = this

        loginViewModel = ViewModelProviders.of(this, loginviewModelFactory).get(
            LoginViewModel::class.java)
        universityViewModel=ViewModelProviders.of(this,universityViewModelFactory)
            .get(UniversityViewModel::class.java)

        universityViewModel.loadUniversities()

        universityViewModel.getUniversities().observe(this, Observer {
            universities.addAll(it)
            val array= arrayOfNulls<String>(universities.size)
            for (i in array.indices){
                array[i]=universities[i].name
            }
            val aa = ArrayAdapter(context, android.R.layout.simple_spinner_item, array)
            // Set layout to use when the list of choices appear
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set Adapter to Spinner
            uni!!.setAdapter(aa)
        })




        view!!.btn_login.setOnClickListener {
            loginViewModel.login(email.text.toString(),password.text.toString(),1,1,activity)
        }


        loginViewModel.getLoadState().observe(this, Observer {
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
//                            loginViewModel.login(email.text.toString(),password.text.toString(),activity)
                        }.show()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.cancelJob()
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

    }


}
