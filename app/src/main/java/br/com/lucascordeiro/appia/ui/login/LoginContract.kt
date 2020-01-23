package br.com.lucascordeiro.appia.ui.login

import br.com.lucascordeiro.appia.ui.base.MvpPresenter
import br.com.lucascordeiro.appia.ui.base.MvpView
import br.com.lucascordeiro.querie.GetMeasurementsQuery


interface LoginMvpPresenter<V :LoginMvpView> : MvpPresenter<V> {
    fun doSingIn(email: String, password: String)
}

interface LoginMvpView : MvpView {
    fun onSingIn()
    fun onSingInNetworkError()
    fun onSingInFail(message: String? = null)
}


