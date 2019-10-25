package com.elemental.atantat.ui.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.elemental.atantat.R
import com.elemental.atantat.adapter.MajorAdapter
import com.elemental.atantat.data.models.Major
import com.elemental.atantat.viewmodel.MajorViewModel.MajorViewModel
import com.elemental.atantat.viewmodel.MajorViewModel.MajorViewModelFactory
import kotlinx.android.synthetic.main.major_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MajorFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val majorViewModelFactory: MajorViewModelFactory by instance()
    private val majors:MutableList<Major> = ArrayList()
    private lateinit var majorAdapter: MajorAdapter
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
        viewModel = ViewModelProvider(this,majorViewModelFactory).get(MajorViewModel::class.java)
        // TODO: Use the ViewModel
        val major= Major(1,"Computer Science",2)
        majors.add(0,major)

        majorAdapter= MajorAdapter(majors)

        major_recycler.apply {
            layoutManager=LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter=majorAdapter
        }



    }

}
