package by.psu.nasaapi.repository

import androidx.lifecycle.LiveData
import by.psu.nasaapi.model.Apod
import kotlinx.coroutines.Deferred

interface Repository {
    /**
     * Получение сегодняшнего изображение от сервиса Nasa
     */
    suspend fun fetchApodAsync(map: Map<String, String>): Deferred<Apod>

    /**
     * Получение списка сохраненных изображенией
     */
    suspend fun getApods(): List<Apod>

    /**
     * Получение сохраненного изображения
     */
    suspend fun getApod(date: String): Apod

    /**
     * Сохранение результата
     */
    suspend fun insert(data: Apod)
}