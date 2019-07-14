package com.elemental.atantat.ui.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.elemental.atantat.R
import com.elemental.atantat.utils.DataLoadState
import com.elemental.atantat.viewmodel.SubjectViewModel.SubjectViewModel
import com.elemental.atantat.viewmodel.SubjectViewModel.SubjectViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SubjectFragment : Fragment(),KodeinAware {
    override val kodein by kodein()
    private val subjectViewModelFactory:SubjectViewModelFactory by instance()
    private lateinit var myView: View

    companion object {
        fun newInstance() = SubjectFragment()
    }

    private lateinit var subjectviewModel: SubjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView= inflater.inflate(R.layout.subject_fragment, container, false)
        return myView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subjectviewModel = ViewModelProviders.of(this,subjectViewModelFactory).get(SubjectViewModel::class.java)

        subjectviewModel.loadSubjects()

        subjectviewModel.getDataLoadState().observe(this, Observer {
            when(it) {
                DataLoadState.LOADING -> {
//                    determinateBar.visibility = View.VISIBLE
                }
                DataLoadState.LOADED -> {
                    //determinateBar.visibility = View.GONE
                }
                DataLoadState.FAILED -> {
                    //determinateBar.visibility = View.VISIBLE
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
        subjectviewModel.cancelJob()
    }

}
