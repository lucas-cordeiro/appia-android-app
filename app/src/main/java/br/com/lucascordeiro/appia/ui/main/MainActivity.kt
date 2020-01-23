package br.com.lucascordeiro.appia.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import br.com.lucascordeiro.appia.R
import br.com.lucascordeiro.appia.ui.base.BaseActivity
import br.com.lucascordeiro.querie.GetMeasurementsQuery
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.graphics.Color
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.data.LineDataSet
import android.graphics.DashPathEffect
import androidx.core.content.ContextCompat
import br.com.lucascordeiro.appia.ui.splash.SplashActivity
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import br.com.lucascordeiro.appia.ui.util.DayAxisValueFormatter
import br.com.lucascordeiro.core.ui.util.CommonsUtil
import com.github.mikephil.charting.components.XAxis
import com.pixplicity.easyprefs.library.Prefs
import io.realm.Realm
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity(), MainMvpView {

    @Inject
    lateinit var presenter: MainMvpPresenter<MainMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent.inject(this)
        presenter.onAttach(this)

        setUp()
    }

    override fun setUp() {
        presenter.doGetMeasurements()
        showLoading()

        btnLogout.setOnClickListener {
            try{
                Prefs.clear()
                Realm.deleteRealm(Realm.getDefaultConfiguration())
            }catch (e: Exception){

            }finally {
                startActivity(Intent(this@MainActivity, SplashActivity::class.java))
                finish()
            }
        }
    }

    override fun onGetMeasurements(list: List<GetMeasurementsQuery.Measurement>) {
       runOnUiThread {
           hideLoading()
           log("get")
           chart.visibility = View.VISIBLE

           val colorHipoglicemia = ContextCompat.getColor(this, R.color.colorHipoglicemia)
           val colorNormal = ContextCompat.getColor(this, R.color.colorNormal)
           val colorHiperglicemia = ContextCompat.getColor(this, R.color.colorHiperglicemia)

           // background color
           chart.setBackgroundColor(Color.WHITE)

           // disable description text
           chart.description.isEnabled = false
           chart.legend.isEnabled = false

           chart.setTouchEnabled(false)

           chart.setDrawGridBackground(false)

           chart.setScaleEnabled(false)

           chart.setPinchZoom(false)


           val llXAxis = LimitLine(9f, "Index 10")
           llXAxis.lineWidth = 4f
           llXAxis.enableDashedLine(10f, 10f, 0f)
           llXAxis.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
           llXAxis.textSize = 10f

           val ll1 = LimitLine(180f, "Normal")
           ll1.lineWidth = 4f
           ll1.lineColor = colorNormal
           ll1.labelPosition = LimitLabelPosition.RIGHT_TOP
           ll1.textSize = 10f

           val ll2 = LimitLine(70f, "Hipoglicemia")
           ll2.lineWidth = 4f
           ll2.lineColor = colorHipoglicemia
           ll2.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
           ll2.textSize = 10f

           val yAxis = chart.axisLeft

           yAxis.addLimitLine(ll1)
           yAxis.addLimitLine(ll2)

           chart.animateX(10)

           val values = ArrayList<Entry>()

           val circleColors: MutableList<Int> = ArrayList()
           val dates: MutableList<Date> = ArrayList()

           list.forEachIndexed { index, measurement ->
               val date = CommonsUtil.parseDate(measurement.createdAt().toString())!!
               dates.add(date)
               log("Date: $date")

               val value = measurement.measurement().toFloat()
               when {
                   value <= 70 -> {
                       circleColors.add(colorHipoglicemia)
                   }
                   value <= 180 -> {
                       circleColors.add(colorNormal)
                   }
                   else -> {
                       circleColors.add(colorHiperglicemia)
                   }
               }
               values.add(Entry(index.toFloat(), value))
           }

           val xAxisFormatter = DayAxisValueFormatter(chart, dates)

           val xAxis = chart.xAxis
           xAxis.position = XAxis.XAxisPosition.BOTTOM
           xAxis.setDrawGridLines(false)
           xAxis.granularity = 1f
           xAxis.labelCount = list.size
           xAxis.valueFormatter = xAxisFormatter

           xAxis.enableGridDashedLine(10f, 10f, 0f)

           val dataSet = LineDataSet(values, "values")

           dataSet.color = ContextCompat.getColor(this, R.color.colorGray)
           dataSet.circleColors = circleColors
           dataSet.valueTextColor = Color.TRANSPARENT

           dataSet.circleRadius = 5f
           dataSet.setDrawCircleHole(false)

           val dataSets =  ArrayList<ILineDataSet>()
           dataSets.add(dataSet)

           val data = LineData(dataSets)

           chart.data = data
       }
    }

    override fun onGetMeasurementsFail() {
        runOnUiThread{
            showSnackError("Erro ao recuperar os dados")
            hideLoading()
        }
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }
}
