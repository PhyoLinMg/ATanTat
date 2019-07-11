package com.elemental.atantat.ui.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.elemental.atantat.R
import com.elemental.atantat.viewmodel.MajorViewModel.MajorViewModel
import com.elemental.atantat.viewmodel.MajorViewModel.MajorViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MajorFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val majorViewModelFactory: MajorViewModelFactory by instance()
    companion object {
        fun newInstance() = MajorFragment()
    }

    private lateinit var viewModel: MajorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.major_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,majorViewModelFactory).get(MajorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
