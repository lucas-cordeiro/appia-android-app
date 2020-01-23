package br.com.lucascordeiro.appia.ui.main

import br.com.lucascordeiro.appia.ui.base.BasePresenter
import br.com.lucascordeiro.core.data.network.api.AppiaApi
import br.com.lucascordeiro.core.data.repository.db.MeasurementsDAO
import br.com.lucascordeiro.core.data.repository.model.MeasurementModel
import br.com.lucascordeiro.core.ui.util.CommonsUtil
import br.com.lucascordeiro.querie.GetMeasurementsQuery

import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.awaitAll
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
                    AppiaApi.doGetMeasurementsQueryAsync()!!
                }else{
                    MeasurementsDAO.doGetMeasurementsAsync().await().map { it.convertFromDao() }
                }

                if(online){
                    val insertJobs : MutableList<Deferred<Any>> = ArrayList()
                    list.forEach {
                        insertJobs.add(MeasurementsDAO.doInsertMeasurementsAsync(MeasurementModel.convertToDao(it)))
                    }
                    insertJobs.awaitAll()
                }
                log("list: ${list.size}")
                mvpView?.onGetMeasurements(list)
            }catch (e: Exception){
                logError(e)
                mvpView?.onGetMeasurementsFail()
            }
        }
    }
}