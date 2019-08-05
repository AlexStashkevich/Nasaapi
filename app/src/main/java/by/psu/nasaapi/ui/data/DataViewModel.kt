package by.psu.nasaapi.ui.data

import androidx.lifecycle.*
import by.psu.nasaapi.model.ApiResponse
import by.psu.nasaapi.model.Apod
import by.psu.nasaapi.repository.Repository
import by.psu.nasaapi.util.MapQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class DataViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            safeFetch()
        }
    }

    private suspend fun safeFetch() {
        try {
            when(val data = repository.safeFetch(MapQuery.defaultQuery())) {
                is ApiResponse.Error -> Timber.e("ApiResponse.Error msg - ${data.errorMessage}")
                is ApiResponse.SuccessEmpty -> Timber.e("ApiResponse.SuccessEmpty")
                is ApiResponse.Success -> repository.insert(data.data)
            }
        } catch (ex: Exception) {
            Timber.e("safeFetch Exception - ${ex.message}")
        }
    }

    val apodLiveData = repository.getApodsLiveData()

    fun removeApod(apod: Apod) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(apod)
        }
    }
}