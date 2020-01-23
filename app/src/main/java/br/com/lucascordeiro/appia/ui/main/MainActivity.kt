package br.com.lucascordeiro.appia.ui.main

import android.os.Bundle
import br.com.lucascordeiro.appia.R
import br.com.lucascordeiro.appia.ui.base.BaseActivity
import br.com.lucascordeiro.querie.GetMeasurementsQuery
import javax.inject.Inject


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
    }

    override fun onGetMeasurements(list: List<GetMeasurementsQuery.Measurement>) {

    }

    override fun onGetMeasurementsFail() {

    }

    override fun hideLoading() {

    }

    override fun showLoading() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }
}
