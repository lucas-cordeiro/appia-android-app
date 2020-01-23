package br.com.lucascordeiro.appia.ui.login

import android.util.Log
import br.com.lucascordeiro.appia.ui.base.BasePresenter
import br.com.lucascordeiro.core.data.network.api.AppiaApi
import br.com.lucascordeiro.core.ui.util.CommonsUtil
import br.com.lucascordeiro.core.ui.util.HAS_USER
import br.com.lucascordeiro.querie.GetMeasurementsQuery
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.google.gson.Gson

import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.List as List1


class LoginPresenter<V :LoginMvpView> @Inject
constructor() : BasePresenter<V>(), LoginMvpPresenter<V> {
    override fun doSingIn(email: String, password: String) {
        jobs add launch {
            try{
                val online = CommonsUtil.isOnlineAsync().await()
                if(online){
                    val result = AppiaApi.doSingInAsync(
                        email = email,
                        password = password
                    ).await()
                    if(result is String){
                        Prefs.putBoolean(HAS_USER, true)
                        mvpView?.onSingIn()
                    }else{
                        mvpView?.onSingInFail((result as List<Error>).first().message())
                    }
                }else{
                    mvpView?.onSingInNetworkError()
                }
            }catch (e: ApolloHttpException){
                logError(e)
                log("ApolloHttpException")
                mvpView?.onSingInFail()
            }catch (e: ApolloException){
                logError(e)
                log("ApolloException")
                mvpView?.onSingInFail()
            }catch (e: Exception){
                logError(e)
                Log.d(TAG, "ApolloException", e)
                mvpView?.onSingInFail()
            }
        }
    }
}