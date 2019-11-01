package com.elemental.atantat.ui.Fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.elemental.atantat.R
import com.elemental.atantat.data.chart
import com.elemental.atantat.viewmodel.MajorViewModel.MajorViewModel
import com.elemental.atantat.viewmodel.MajorViewModel.MajorViewModelFactory
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


class MajorFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val majorViewModelFactory: MajorViewModelFactory by instance()
    private val charts:MutableList<chart> = ArrayList()

    companion object {
        fun newInstance() = MajorFragment()
    }

    private lateinit var viewModel: MajorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.major_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,majorViewModelFactory).get(MajorViewModel::class.java)
        // TODO: Use the ViewModel
        val anyChartView = any_chart_view
        anyChartView.setProgressBar(progress_bar)
        val cartesian:Cartesian = AnyChart.column()

        val data:MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("English",70))
        data.add(ValueDataEntry("Major 1",60))
        data.add(ValueDataEntry("Major 2",30))
        data.add(ValueDataEntry("Major 3",75))
        data.add(ValueDataEntry("Major 4",92))
        data.add(ValueDataEntry("Major 5",100))

        val column:Column = cartesian.column(data)

        column.tooltip()
            .titleFormat("{%X}")
            .position(Position.AUTO)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0.0)
            .offsetY(5.0)
            .format("{%Value}")

        cartesian.animation(true)
        cartesian.title("Your Roll Call Graph")

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
