package com.elemental.atantat

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.elemental.atantat.adapter.NotiAdapter
import com.elemental.atantat.data.models.Subject
import com.elemental.atantat.viewmodel.SubjectViewModel.SubjectViewModel
import com.elemental.atantat.viewmodel.SubjectViewModel.SubjectViewModelFactory

import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.content_notification.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import androidx.appcompat.app.AlertDialog


class NotificationActivity : AppCompatActivity(),NotiAdapter.OnItemClickedListener , KodeinAware {
    private lateinit var notiAdapter:NotiAdapter
    private val subjects:MutableList<Subject> = ArrayList()
    override val kodein by kodein()
    private val subjectViewModelFactory: SubjectViewModelFactory by instance()

    private lateinit var subjectViewModel: SubjectViewModel
    override fun onItemClicked(subject: Subject) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(subject.name)
        builder.setMessage("Total Time:${subject.yes+subject.no} \n Your attended time:${subject.yes}")
            .setCancelable(false)
            .setPositiveButton("OK", DialogInterface.OnClickListener {
                dialog, id -> dialog.cancel()
        })
        val alertDialog=builder.create()
        alertDialog.setTitle(subject.name)
        alertDialog.show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        setSupportActionBar(toolbar)


        subjectViewModel = ViewModelProvider(this,subjectViewModelFactory).get(SubjectViewModel::class.java)
        notiAdapter= NotiAdapter(subjects,this)


        noti_recycler.apply {
            layoutManager=LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter=notiAdapter
        }
        Log.d("subs",subjects.toString())
        subjectViewModel.loadSubjects()
        subjectViewModel.getSubjects().observe(this, Observer {
            subjects.clear()
            subjects.addAll(it)
            notiAdapter.notifyDataSetChanged()
        })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


        noti_recycler.apply {

        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
