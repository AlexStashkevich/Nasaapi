package by.psu.nasaapi.db

import androidx.room.Database
import androidx.room.RoomDatabase
import by.psu.nasaapi.model.Apod


@Database(
    entities = [Apod::class],
    version = 1,
    exportSchema = false
)
abstract class NasaDataBase: RoomDatabase() {
    abstract fun apodDao(): ApodDao
}