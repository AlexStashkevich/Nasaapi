package by.psu.nasaapi.db

import androidx.lifecycle.LiveData
import androidx.room.*
import by.psu.nasaapi.model.Apod

@Dao
interface ApodDao {
    /**
     * Получение observable списка сохраненных данных
     */
    @Query("SELECT * FROM Apod ORDER BY date DESC")
    fun getApodsLiveData(): LiveData<List<Apod>>

    /**
     * Получение сохраненной записи
     */
    @Query("SELECT * FROM Apod WHERE date = :date")
    suspend fun getApodByDate(date: String): Apod

    /**
     * Сохранение экземпляра данных
      */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(apod: Apod)

    /**
     * Удаление экземпляра данных
      */
    @Delete
    suspend fun delete(apod: Apod)
}