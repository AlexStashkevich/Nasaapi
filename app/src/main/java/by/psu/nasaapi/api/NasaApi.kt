package by.psu.nasaapi.api

import by.psu.nasaapi.model.Apod
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NasaApi {
    @GET("planetary/apod")
    fun fetchApodAsync(@QueryMap param: Map<String, String>): Deferred<Apod>
}