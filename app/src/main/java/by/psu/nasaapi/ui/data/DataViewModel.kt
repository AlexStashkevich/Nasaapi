package by.psu.nasaapi.ui.data

import androidx.lifecycle.*
import by.psu.nasaapi.model.Apod
import by.psu.nasaapi.repository.Repository
import by.psu.nasaapi.util.MapQuery
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class DataViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _apods = MutableLiveData<List<Apod>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            initData()
            fetchData(null)
            updateApods()
        }
    }

    val apods: LiveData<List<Apod>>
        get() = _apods

    fun addApod(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchData(date)
            updateApods()
        }
    }

    private suspend fun initData() {
        val cachedData = repository.getApods()
        _apods.postValue(cachedData)
    }

    private suspend fun fetchData(date: String?) {
        val map = when (date) {
            null -> MapQuery.defaultQuery()
            else -> MapQuery.byDateQuery(date)
        }
        val fetchData = repository.fetchApodAsync(map)
        repository.insert(fetchData.await())
    }

    private suspend fun updateApods() {
        val updatedData = repository.getApods()
        _apods.postValue(updatedData)
    }
}