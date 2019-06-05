package com.elemental.atantat.ui.Fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

import com.elemental.atantat.R
import com.elemental.atantat.repository.loginRepo.LoginRepositoryImpl
import com.elemental.atantat.repository.signupRepo.SignUpRepository
import com.elemental.atantat.repository.signupRepo.SignUpRepositoryImpl
import com.elemental.atantat.viewmodel.LoginViewModel.LoginViewModel
import com.elemental.atantat.viewmodel.LoginViewModel.LoginViewModelFactory
import com.elemental.atantat.viewmodel.SignUpViewModel.SignUpViewModel
import com.elemental.atantat.viewmodel.SignUpViewModel.SignUpViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_login.view.email
import kotlinx.android.synthetic.main.fragment_login.view.password
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RegisterFragment : Fragment() {

    private lateinit var viewModel:SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_register, container, false);
        // Inflate the layout for this fragment
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel = ViewModelProviders.of(this, SignUpViewModelFactory(SignUpRepositoryImpl(context!!))).get(
            SignUpViewModel::class.java)
        view!!.btn_signup.setOnClickListener {
            //Log.d("email",password.text.toString())
            viewModel.signup(name.text.toString(),email.text.toString(),password.text.toString(),password_confirmation.text.toString(),activity)

        }
    }


}
