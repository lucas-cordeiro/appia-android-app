package br.com.lucascordeiro.appia

import android.app.Application
import android.content.ContextWrapper
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatDelegate
import br.com.lucascordeiro.appia.di.component.ApplicationComponent
import br.com.lucascordeiro.appia.di.component.DaggerApplicationComponent
import br.com.lucascordeiro.appia.di.module.ApplicationModule
import com.pixplicity.easyprefs.library.Prefs

class BaseApp: Application() {


    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)


        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()


        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this)).build()
        applicationComponent.inject(this)
    }
}
