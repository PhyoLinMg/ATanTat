package com.elemental.atantat.ui.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.elemental.atantat.R
import com.elemental.atantat.viewmodel.MajorViewModel.MajorViewModel

class MajorFragment : Fragment() {

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
        viewModel = ViewModelProviders.of(this).get(MajorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
