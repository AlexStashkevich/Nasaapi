package by.psu.nasaapi.di

import android.app.Application
import androidx.room.Room
import by.psu.nasaapi.api.NasaApi
import by.psu.nasaapi.db.ApodDao
import by.psu.nasaapi.db.NasaDataBase
import by.psu.nasaapi.repository.Repository
import by.psu.nasaapi.repository.RepositoryImp
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideApiService(): NasaApi {
        return Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): NasaDataBase {
        return Room
            .databaseBuilder(app, NasaDataBase::class.java, "nasa.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideApodDao(db: NasaDataBase): ApodDao {
        return db.apodDao()
    }

    @Singleton
    @Provides
    fun provideRepository(
        apodDao: ApodDao,
        nasaApi: NasaApi
    ): Repository {
        return RepositoryImp(
            dao = apodDao,
            api = nasaApi)
    }
}