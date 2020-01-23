package br.com.lucascordeiro.core.data.network.api

import br.com.lucascordeiro.core.BuildConfig.BASE_URL
import br.com.lucascordeiro.core.ui.util.TOKEN_ACCESS
import br.com.lucascordeiro.querie.GetMeasurementsQuery
import br.com.lucascordeiro.querie.SingInMutation
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.request.RequestHeaders
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class AppiaApi() {
    companion object{
        private var apolloClient: ApolloClient? = null

        fun makeApiClient(): ApolloClient {
            return ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(makeHttpClient())
                .build()
        }

        private fun makeHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()
        }

        private fun getApolloClient() : ApolloClient {
            return if(apolloClient!=null)
                apolloClient!!
            else
                makeApiClient()
        }

        suspend fun doSingInAsync(email: String, password: String) = withContext(IO){
            async{
                    val singInMutation = SingInMutation(email, password)
                val request = getApolloClient().mutate(singInMutation).toDeferred().await()
                if(request.hasErrors()){
                    return@async request.errors()
                }
                    val token = request.data()!!.signIn()!!.token()
                    Prefs.putString(TOKEN_ACCESS, token)
                    token
            }
        }

        suspend fun doGetMeasurementsQueryAsync() = withContext(IO) {
            val getMeasurementsQuery = GetMeasurementsQuery.builder().build()
            val measures = getApolloClient()
                .query(getMeasurementsQuery)
                .requestHeaders(
                RequestHeaders.builder().addHeader("token",Prefs.getString(TOKEN_ACCESS, "")).build()
                )
                .toDeferred()
                .await()
                .data()!!
                .measurements()
            measures
        }
    }
}