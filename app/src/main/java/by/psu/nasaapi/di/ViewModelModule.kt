package by.psu.nasaapi.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.psu.nasaapi.ui.data.DataViewModel
import by.psu.nasaapi.ui.datedf.DateViewModel
import by.psu.nasaapi.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DataViewModel::class)
    abstract fun bindDataViewModel(dataViewModel: DataViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DateViewModel::class)
    abstract fun bindDateViewModel(dateViewModel: DateViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}