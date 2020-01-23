package br.com.lucascordeiro.appia.di.component

import br.com.lucascordeiro.appia.di.annotations.PerActivity
import br.com.lucascordeiro.appia.di.module.FragmentModule
import dagger.Component

@PerActivity
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {
//    fun inject(homeFragment: HomeFragment)
}
