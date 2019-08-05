package by.psu.nasaapi.di

import by.psu.nasaapi.ui.data.DataFragment
import by.psu.nasaapi.ui.date.DatePickerDialogFragment
import by.psu.nasaapi.ui.image.ImageFragmentDialog
import by.psu.nasaapi.ui.info.InfoImageFragment
import by.psu.nasaapi.ui.info.InfoVideoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeDataFragment(): DataFragment

    @ContributesAndroidInjector
    abstract fun contributeInfoImageFragment(): InfoImageFragment

    @ContributesAndroidInjector
    abstract fun contributeDatePickerDialogFragment(): DatePickerDialogFragment

    @ContributesAndroidInjector
    abstract fun contributeImageDialogFragment(): ImageFragmentDialog

    @ContributesAndroidInjector
    abstract fun contributeInfoVideoFragment(): InfoVideoFragment
}