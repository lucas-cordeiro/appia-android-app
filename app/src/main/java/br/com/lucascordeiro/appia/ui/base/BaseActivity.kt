package br.com.lucascordeiro.appia.ui.base

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.os.PersistableBundle
import android.text.SpannableString
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.lucascordeiro.appia.BaseApp
import br.com.lucascordeiro.appia.R
import br.com.lucascordeiro.appia.di.component.ActivityComponent
import br.com.lucascordeiro.appia.di.component.DaggerActivityComponent
import br.com.lucascordeiro.appia.di.module.ActivityModule
import br.com.lucascordeiro.core.ui.util.showSnack

abstract class BaseActivity : AppCompatActivity(), MvpView {

    val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .applicationComponent((application as BaseApp).applicationComponent)
            .build()
    }

    override fun onError(message: String) {
        logError(message)
        showMessage(message)
    }

    override fun onError(@StringRes resId: Int) {
        onError(getString(resId))
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(@StringRes resId: Int) {
        showMessage(getString(resId))
    }

    override fun showSnack(resId: Int) {
        showSnack(getString(resId))
    }

    override fun showSnack(message: String) {
        message.showSnack(findViewById(R.id.container), backgroundColor = R.color.colorAccent)
    }

    protected fun showSnackError(message: String) {
        message.showSnack(findViewById(R.id.container), backgroundColor = R.color.colorRed)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        log("OnCreate")
        OPEN = true
    }

    override fun onStart() {
        super.onStart()
        log("OnStart")
    }

    override fun onResume() {
        super.onResume()
        log("OnResume")
    }

    override fun onRestart() {
        super.onRestart()
        log("OnResume")
    }

    override fun onPause() {
        super.onPause()
        log("OnPause")
    }

    override fun onStop() {
        super.onStop()
        log("OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        OPEN = false
    }

    fun log(message: String) {
        Log.d(TAG, message)
    }

    fun logError(message: String) {
        Log.e(TAG, message)
    }



    fun setToolbarTitle(title: String?) {
        supportActionBar?.title = if (!title.isNullOrEmpty()) title else resources.getString(R.string.app_name)
    }

    val TAG: String
        get() = this::class.java.simpleName

    protected abstract fun setUp()

    protected abstract fun showLoading()

    protected abstract fun hideLoading()

    companion object {
        var OPEN = false
    }
}