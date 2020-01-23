package br.com.lucascordeiro.appia.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import br.com.lucascordeiro.appia.di.annotations.ActivityContext
import br.com.lucascordeiro.appia.ui.login.LoginMvpPresenter
import br.com.lucascordeiro.appia.ui.login.LoginMvpView
import br.com.lucascordeiro.appia.ui.login.LoginPresenter
import br.com.lucascordeiro.appia.ui.main.MainMvpPresenter
import br.com.lucascordeiro.appia.ui.main.MainMvpView
import br.com.lucascordeiro.appia.ui.main.MainPresenter
import dagger.Module
import dagger.Provides


@Module
class ActivityModule(private val mActivity: AppCompatActivity) {

    @Provides
    @ActivityContext
    internal fun provideContext(): Context {
        return mActivity
    }

    @Provides
    internal fun provideActivity(): AppCompatActivity {
        return mActivity
    }

//    @Provides
//    internal fun providSplashMvpPresenter(presenter: SplashPresenter<SplashMvpView>): SplashMvpPresenter<SplashMvpView> =
//        presenter

    @Provides
        internal fun providMainMvpPresenter(presenter: MainPresenter<MainMvpView>): MainMvpPresenter<MainMvpView> =
        presenter

    @Provides
    internal fun providLoginMvpPresenter(presenter: LoginPresenter<LoginMvpView>): LoginMvpPresenter<LoginMvpView> =
        presenter

}