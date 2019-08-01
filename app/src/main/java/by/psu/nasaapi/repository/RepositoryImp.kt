package by.psu.nasaapi.repository

import by.psu.nasaapi.api.NasaApi
import by.psu.nasaapi.db.ApodDao
import by.psu.nasaapi.model.Apod
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    val dao: ApodDao,
    val api: NasaApi
): Repository {
    override suspend fun fetchApodAsync(map: Map<String, String>): Deferred<Apod> = api.fetchApodAsync(map)

    override suspend fun insert(data: Apod) = dao.insert(apod = data)

    override suspend fun getApod(date: String): Apod = dao.getApodByDate(date = date)

    override suspend fun getApods(): List<Apod>  = dao.getApods()
}