package by.psu.nasaapi.di

import by.psu.nasaapi.ui.data.DataFragment
import by.psu.nasaapi.ui.info.InfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeDataFragment(): DataFragment

    @ContributesAndroidInjector
    abstract fun contributeInfoFragment(): InfoFragment
}