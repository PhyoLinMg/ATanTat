package com.elemental.atantat.ui.Fragment



import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProviders

import com.elemental.atantat.R
import com.elemental.atantat.repository.loginRepo.LoginRepositoryImpl
import com.elemental.atantat.viewmodel.LoginViewModel.LoginViewModel
import com.elemental.atantat.viewmodel.LoginViewModel.LoginViewModelFactory

import kotlinx.android.synthetic.main.fragment_login.view.*

import org.kodein.di.KodeinAware

import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


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

    private lateinit var viewModel:LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_login, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val email:String=view!!.email.text.toString()
        val password:String=view!!.password.text.toString()

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        view!!.btn_login.setOnClickListener { view ->
            viewModel.login(email,password)
        }
    }

}


