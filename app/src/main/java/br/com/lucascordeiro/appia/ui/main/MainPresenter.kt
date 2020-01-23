package br.com.lucascordeiro.appia.ui.main

import br.com.lucascordeiro.appia.ui.base.BasePresenter
import br.com.lucascordeiro.core.data.network.api.AppiaApi
import br.com.lucascordeiro.core.ui.util.CommonsUtil
import br.com.lucascordeiro.querie.GetMeasurementsQuery

import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class MainPresenter<V :MainMvpView> @Inject
constructor() : BasePresenter<V>(), MainMvpPresenter<V> {
    override fun doGetMeasurements() {
        jobs add launch {
            try{
                val online = CommonsUtil.isOnlineAsync().await()
                val list: List<GetMeasurementsQuery.Measurement> = if(online){
                    AppiaApi.doGetMeasurementsQuery()!!
                }else{
                    ArrayList()
                }
                mvpView?.onGetMeasurements(list)
            }catch (e: Exception){
                mvpView?.onGetMeasurementsFail()
            }
        }
    }
}