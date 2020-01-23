package br.com.lucascordeiro.appia.di.component

import android.app.Application
import android.content.Context
import br.com.lucascordeiro.appia.BaseApp
import br.com.lucascordeiro.appia.di.annotations.ApplicationContext
import br.com.lucascordeiro.appia.di.module.ApplicationModule
import br.com.lucascordeiro.appia.di.module.NetworkModule
import dagger.Component

@Component(modules =  [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {

    fun inject(app: BaseApp)
    fun application(): Application
    @ApplicationContext
    fun context(): Context
}