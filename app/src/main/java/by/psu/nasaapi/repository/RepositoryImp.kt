package by.psu.nasaapi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import by.psu.nasaapi.api.NasaApi
import by.psu.nasaapi.db.ApodDao
import by.psu.nasaapi.model.ApiResponse
import by.psu.nasaapi.model.Apod
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    val dao: ApodDao,
    val api: NasaApi
): Repository {
    override suspend fun safeFetch(map: Map<String, String>): ApiResponse<Apod> =
        ApiResponse.create(api.fetchApodAsync(map))

    override suspend fun insert(data: Apod) = dao.insert(apod = data)

    override suspend fun getApod(date: String): Apod = dao.getApodByDate(date = date)

    override suspend fun delete(apod: Apod) = dao.delete(apod)

    override fun getApodsLiveData(): LiveData<List<Apod>> = liveData(Dispatchers.IO) {
            emitSource(source = dao.getApodsLiveData())
    }
}