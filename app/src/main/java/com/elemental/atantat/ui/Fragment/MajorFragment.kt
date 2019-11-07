package com.elemental.atantat.ui.Fragment


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elemental.atantat.R
import kotlinx.android.synthetic.main.major_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Column
import com.anychart.enums.HoverMode
import com.anychart.enums.TooltipPositionMode
import com.anychart.enums.Anchor
import com.anychart.enums.Position
import com.elemental.atantat.db.AtanTatDatabase
import com.elemental.atantat.utils.Calculations
import com.elemental.atantat.viewmodel.SubjectViewModel.SubjectViewModel
import com.elemental.atantat.viewmodel.SubjectViewModel.SubjectViewModelFactory
import org.jetbrains.anko.doAsync


class MajorFragment : Fragment(), KodeinAware {
    override val kodein by kodein()

    private val subjectViewModelFactory: SubjectViewModelFactory by instance()
    private val calculations=Calculations()

    companion object {
        fun newInstance() = MajorFragment()
    }

    private lateinit var viewModel: SubjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.major_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this,subjectViewModelFactory).get(SubjectViewModel::class.java)
        // TODO: Use the ViewModel

        val db=AtanTatDatabase(context!!)
        val anyChartView = any_chart_view
        anyChartView.setProgressBar(progress_bar)

//        val colorDrawable=ColorDrawable(ContextCompat.getColor(context!!,R.color.black))
//        anyChartView.setBackgroundColor(resources.getColor(R.color.black))

        val cartesian:Cartesian = AnyChart.column()



        val data:MutableList<DataEntry> = ArrayList()
        doAsync {
            val subjects=db.SubjectDao().subjects()
            for(i in subjects.indices){
                data.add(ValueDataEntry(subjects[i].name,calculations.calculatePercentage(subjects[i].yes,subjects[i].no)))
            }

        }

        val column:Column = cartesian.column(data)

        column.tooltip()
            .titleFormat("{%X}")
            .position(Position.AUTO)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0.0)
            .offsetY(5.0)
            .format("{%Value}%")

        cartesian.animation(true)
        cartesian.title("Your Roll Call Number")

        cartesian.yScale().minimum(0.0)
        cartesian.yScale().maximum(100)

        cartesian.yAxis(0).labels().format("{%Value}%")

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)

        cartesian.xAxis(0).title("Major")
        cartesian.yAxis(0).title("Roll Call Percentage")


        anyChartView.setChart(cartesian)


    }

}
