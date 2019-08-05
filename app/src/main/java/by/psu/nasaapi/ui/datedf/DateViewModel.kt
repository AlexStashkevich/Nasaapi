package by.psu.nasaapi.ui.datedf

import androidx.lifecycle.ViewModel
import by.psu.nasaapi.model.ApiResponse
import by.psu.nasaapi.repository.Repository
import by.psu.nasaapi.util.MapQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class DateViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    fun addApod(date: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                when(val data = repository.safeFetch(MapQuery.byDateQuery(date))) {
                    is ApiResponse.Error -> Timber.e("ApiResponse.Error msg - ${data.errorMessage}")
                    is ApiResponse.SuccessEmpty -> Timber.e("ApiResponse.SuccessEmpty")
                    is ApiResponse.Success -> repository.insert(data.data)
                }
            } catch (ex: Exception) {
                Timber.e("safeFetch Exception - ${ex.message}")
            }
        }
    }
}