package br.com.lucascordeiro.appia.di.module

import android.content.Context
import androidx.fragment.app.Fragment
import br.com.lucascordeiro.appia.di.annotations.ActivityContext
import dagger.Module
import dagger.Provides


@Module
class FragmentModule(private val mFragment: Fragment) {

    @Provides
    @ActivityContext
    internal fun provideContext(): Context {
        return mFragment.context!!
    }

    @Provides
    internal fun provideFragment(): Fragment {
        return mFragment
    }

//    @Provides
//    internal fun providHomeMvpPresenter(presenter: HomePresenter<HomeMvpView>): HomeMvpPresenter<HomeMvpView> =
//        presenter

}