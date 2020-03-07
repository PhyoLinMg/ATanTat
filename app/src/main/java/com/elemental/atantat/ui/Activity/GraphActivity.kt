package com.elemental.atantat.ui.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import com.elemental.atantat.R
import com.elemental.atantat.data.models.Subject
import com.elemental.atantat.db.AtanTatDatabase
import com.elemental.atantat.utils.Calculations
import com.elemental.atantat.utils.getViewModel
import com.elemental.atantat.viewmodel.SubjectViewModel.SubjectViewModel
import com.elemental.atantat.viewmodel.SubjectViewModel.SubjectViewModelFactory
import com.elemental.atantat.viewmodel.TestingViewModel.TestingViewModel

import kotlinx.android.synthetic.main.activity_graph.*
import kotlinx.android.synthetic.main.content_graph.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class GraphActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val subjectViewModelFactory: SubjectViewModelFactory by instance()
    private val calculations= Calculations()
    private val subjects:MutableList<Subject> = ArrayList()
    private lateinit var subjectviewModel: SubjectViewModel

    private val db=AtanTatDatabase(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subjectviewModel = ViewModelProvider(this,subjectViewModelFactory).get(SubjectViewModel::class.java)
        setContentView(R.layout.activity_graph)
        setSupportActionBar(toolbar)

        val vm:TestingViewModel by lazy {
            getViewModel{ TestingViewModel(this)}
        }

        Log.d("testingViewModel",vm.returnText())
        val anyChartView = any_chart_view
        anyChartView.setProgressBar(progress_bar)
        val data:MutableList<DataEntry> = ArrayList()
        subjectviewModel.loadSubjects()
        subjectviewModel.getSubjects().observe(this, Observer {
            subjects.addAll(it)
            for(subject in subjects){
                data.add(ValueDataEntry(subject.name,calculations.calculatePercentage(subject.yes,subject.no)))

            }
            val cartesian = AnyChart.column()
            val column = cartesian.column(data)

            column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0.0)
                .offsetY(5.0)
                .format("{%Value}%")

            cartesian.animation(true)
            cartesian.title("Your Roll Call Graph")

            cartesian.yScale().minimum(0.0)
            cartesian.yScale().maximum(100.0)

            cartesian.yAxis(0).labels().format("{%Value}%")

            cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
            cartesian.interactivity().hoverMode(HoverMode.BY_X)

            cartesian.xAxis(0).title("Majors")
            cartesian.yAxis(0).title("Roll Call Percentage")

            anyChartView.setChart(cartesian)
        })



//
//        data.add(ValueDataEntry("Rouge", 89))
//        data.add(ValueDataEntry("Foundation", 85))
//        data.add(ValueDataEntry("Mascara", 85))
//        data.add(ValueDataEntry("Lip gloss", 95))
//        data.add(ValueDataEntry("Lipstick", 100))
//        data.add(ValueDataEntry("Nail polish", 30))
//        data.add(ValueDataEntry("Eyebrow pencil", 70))
//        data.add(ValueDataEntry("Eyeliner", 100))
//        data.add(ValueDataEntry("Eyeshadows", 20))







        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
