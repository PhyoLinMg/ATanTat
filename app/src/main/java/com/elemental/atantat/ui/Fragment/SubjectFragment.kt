package com.elemental.atantat.ui.Fragment

import android.graphics.Color
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.elemental.atantat.R
import com.elemental.atantat.adapter.SubjectAdapter
import com.elemental.atantat.data.models.Subject
import com.elemental.atantat.db.AtanTatDatabase
import com.elemental.atantat.utils.DataLoadState
import com.elemental.atantat.viewmodel.SubjectViewModel.SubjectViewModel
import com.elemental.atantat.viewmodel.SubjectViewModel.SubjectViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.subject_fragment.*
import org.jetbrains.anko.doAsync
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SubjectFragment : Fragment(),KodeinAware {
    override val kodein by kodein()
    private val subjectViewModelFactory:SubjectViewModelFactory by instance()
    private lateinit var subjectAdapter: SubjectAdapter
    private lateinit var myView: View

    private val subjects: MutableList<Subject> = ArrayList()
    private lateinit var subjectviewModel: SubjectViewModel

    companion object {
        fun newInstance() = SubjectFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView= inflater.inflate(R.layout.subject_fragment, container, false)
        return myView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("created","subject created")
        subjectviewModel = ViewModelProvider(this,subjectViewModelFactory).get(SubjectViewModel::class.java)
        refresh.setColorSchemeColors(Color.BLUE, Color.CYAN, Color.RED)
        subjectAdapter= SubjectAdapter(subjects)
        val db:AtanTatDatabase= AtanTatDatabase.invoke(context!!)
        sub_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = subjectAdapter
        }
        subjectviewModel.loadSubjects()

        subjectviewModel.getSubjects().observe(viewLifecycleOwner, Observer {
            subjects.addAll(it)
            subjectAdapter.notifyDataSetChanged()
        })
        Log.d("sub",subjects.toString())

        refresh.setOnRefreshListener {
            subjects.clear()
            doAsync {
                subjects.addAll(db.SubjectDao().subjects())
            }
            subjectAdapter.notifyDataSetChanged()
            refresh.isRefreshing=false
        }


        subjectviewModel.getDataLoadState().observe(viewLifecycleOwner, Observer {
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
                        }.show()
                }
                DataLoadState.FAILURE ->{

                }
                DataLoadState.SUCCESS ->{

                }
            }
        })

    }
    override fun onPause() {
        super.onPause()
        Log.d("paused","fragment paused")

    }

    override fun onResume() {
        super.onResume()

        subjectviewModel.getDataLoadState().observe(this, Observer {
            Log.d("state",subjectviewModel.getDataLoadState().value.toString())
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
                DataLoadState.FAILURE ->{

                }
                DataLoadState.SUCCESS ->{

                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        subjectviewModel.cancelJob()
    }

}
