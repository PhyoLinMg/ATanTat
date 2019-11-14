package com.elemental.atantat.ui.Fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders

import com.elemental.atantat.R
import com.elemental.atantat.data.models.Major
import com.elemental.atantat.data.models.University

import com.elemental.atantat.repository.signupRepo.SignUpRepositoryImpl
import com.elemental.atantat.utils.DataLoadState
import com.elemental.atantat.utils.SharedPreference
import com.elemental.atantat.viewmodel.MajorViewModel.MajorViewModel
import com.elemental.atantat.viewmodel.MajorViewModel.MajorViewModelFactory

import com.elemental.atantat.viewmodel.SignUpViewModel.SignUpViewModel
import com.elemental.atantat.viewmodel.SignUpViewModel.SignUpViewModelFactory
import com.elemental.atantat.viewmodel.UniversityViewModel.UniversityViewModel
import com.elemental.atantat.viewmodel.UniversityViewModel.UniversityViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*


import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.determinateBar
import kotlinx.android.synthetic.main.fragment_register.email
import kotlinx.android.synthetic.main.fragment_register.major
import kotlinx.android.synthetic.main.fragment_register.password
import kotlinx.android.synthetic.main.fragment_register.uni
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
    private val universityViewModelFactory: UniversityViewModelFactory by instance()
    private val majorViewModelFactory: MajorViewModelFactory by instance()

    private lateinit var myView: View
    private lateinit var signupViewModel:SignUpViewModel
    private lateinit var universityViewModel:UniversityViewModel
    private lateinit var majorViewModel:MajorViewModel
    private lateinit var sharedPreference:SharedPreference

    private val universities: MutableList<University> = ArrayList()
    private val majors:MutableList<Major> = ArrayList()

    private val uniID: MutableLiveData<Int> = MutableLiveData()
    private val majorID: MutableLiveData<Int> = MutableLiveData()

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
        signupViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            SignUpViewModel::class.java)
        universityViewModel=ViewModelProviders.of(this,universityViewModelFactory)
            .get(UniversityViewModel::class.java)
        majorViewModel=ViewModelProviders.of(this,majorViewModelFactory)
            .get(MajorViewModel::class.java)

        universityViewModel.loadUniversities()

        majorViewModel.loadMajors()

        universityViewModel.getUniversities().observe(viewLifecycleOwner, Observer {
            universities.addAll(it)
            val uniNames= arrayOfNulls<String>(universities.size)
            for (i in uniNames.indices){
                uniNames[i]=universities[i].name
            }
            val aa = ArrayAdapter(context, android.R.layout.simple_spinner_item, uniNames)
            // Set layout to use when the list of choices appear
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set Adapter to Spinner
            uni!!.adapter = aa
            uni.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    val uni_id=position+1
                    uniID.postValue(uni_id)

                }

            }
        })


        majorViewModel.getMajors().observe(viewLifecycleOwner, Observer {
            majors.addAll(it)
            val majorNames= arrayOfNulls<String>(majors.size)
            for(i in majorNames.indices)
                majorNames[i]=majors[i].name
            val aa = ArrayAdapter(context, android.R.layout.simple_spinner_item, majorNames)
            // Set layout to use when the list of choices appear
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set Adapter to Spinner
            major!!.adapter = aa
            major.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    val major_id=position+1
                    majorID.postValue(major_id)
                }

            }


        })


        view!!.btn_signup.setOnClickListener {
            signupViewModel.signup(name.text.toString(),email.text.toString(),password.text.toString(),password_confirmation.text.toString(),uniID.value!!,majorID.value!!,activity)
        }
        signupViewModel.getLoadState().observe(viewLifecycleOwner, Observer {
            Log.d("signup",signupViewModel.getLoadState().value.toString())
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
                            signupViewModel.signup(name.text.toString(),email.text.toString(),password.text.toString(),password_confirmation.text.toString(),1,1,activity)
                        }.show()
                }
            }
        })
        loadStateUni()
        loadStateMajor()
    }

    override fun onDestroy() {
        super.onDestroy()
        signupViewModel.cancelJob()
        universityViewModel.cancelJob()
        majorViewModel.cancelJob()
    }

    private fun loadStateUni(){
        universityViewModel.getDataLoadState().observe(viewLifecycleOwner, Observer {
            Log.d("unistate",universityViewModel.getDataLoadState().value.toString())
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
    private fun loadStateMajor(){
        majorViewModel.getDataLoadState().observe(viewLifecycleOwner, Observer {
            Log.d("majorstate",majorViewModel.getDataLoadState().value.toString())
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


}
