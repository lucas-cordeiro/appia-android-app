package br.com.lucascordeiro.appia.di.module

import android.app.Application
import android.content.Context
import br.com.lucascordeiro.appia.di.annotations.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule (private val mApplication: Application) {
    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return mApplication
    }

    @Provides
    internal fun provideApplication(): Application {
        return mApplication
    }
}