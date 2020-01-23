package br.com.lucascordeiro.appia.ui.base

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * onAttach() and onDetach(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
open class BasePresenter<V : MvpView>  :
    MvpPresenter<V>, CoroutineScope {

    override val coroutineContext = Default

    protected val jobs = ArrayList<Job>()

    infix fun ArrayList<Job>.add(job: Job) { this.add(job) }

    var mvpView: V? = null
        private set


    override fun onAttach(mvpView: V) {
        this.mvpView = mvpView
    }

    override fun onDetach() {
        doClearJobs()
        mvpView = null
    }

    protected fun log(message: String) {
        Log.d(TAG, message)
    }

    protected fun logError(e: Exception){
        Log.d(TAG, e.message?:"Null", e)
    }

    protected fun doClearJobs() {
        jobs.forEach { if(!it.isCancelled) it.cancel() }
    }


    override val TAG: String
        get() = this::class.java.simpleName

    val isViewAttached: Boolean
        get() = mvpView != null

}
