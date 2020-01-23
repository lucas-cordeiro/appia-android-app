package br.com.lucascordeiro.appia.ui.main

import br.com.lucascordeiro.appia.ui.base.MvpPresenter
import br.com.lucascordeiro.appia.ui.base.MvpView
import br.com.lucascordeiro.querie.GetMeasurementsQuery


interface MainMvpPresenter<V :MainMvpView> : MvpPresenter<V> {
    fun doGetMeasurements()
}

interface MainMvpView : MvpView {
    fun onGetMeasurements(list: List<GetMeasurementsQuery.Measurement>)
    fun onGetMeasurementsFail()
}


