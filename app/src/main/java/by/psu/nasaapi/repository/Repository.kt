package by.psu.nasaapi.repository

import androidx.lifecycle.LiveData
import by.psu.nasaapi.model.ApiResponse
import by.psu.nasaapi.model.Apod
import retrofit2.Response

interface Repository {
    /**
     * Безопасное получение данных
     */
    suspend fun safeFetch(map: Map<String, String>):ApiResponse<Apod>

    /**
     * Получение observable списка сохраненных данных
     */
    fun getApodsLiveData(): LiveData<List<Apod>>

    /**
     * Получение сохраненных данных
     */
    suspend fun getApod(date: String): Apod

    /**
     * Сохранение результата
     */
    suspend fun insert(data: Apod)

    /**
     * Удаление элемента
     */
    suspend fun delete(apod: Apod)
}