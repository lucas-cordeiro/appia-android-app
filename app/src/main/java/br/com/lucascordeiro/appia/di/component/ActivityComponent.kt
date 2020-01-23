package br.com.lucascordeiro.appia.di.component

import br.com.lucascordeiro.appia.ui.main.MainActivity
import br.com.lucascordeiro.appia.di.annotations.PerActivity
import br.com.lucascordeiro.appia.di.module.ActivityModule
import br.com.lucascordeiro.appia.ui.login.LoginActivity
import dagger.Component


@PerActivity
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {
//    fun inject(splashActivity: SplashActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(mainActivity: MainActivity)
}
