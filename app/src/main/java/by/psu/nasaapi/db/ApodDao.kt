package by.psu.nasaapi.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.psu.nasaapi.model.Apod

@Dao
interface ApodDao {
    @Query("SELECT * FROM Apod ORDER BY date DESC")
    suspend fun getApods(): List<Apod>

    @Query("SELECT * FROM Apod WHERE date = :date")
    suspend fun getApodByDate(date: String): Apod

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(apod: Apod)
}