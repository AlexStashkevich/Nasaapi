package by.psu.nasaapi.api

import by.psu.nasaapi.model.Apod
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NasaApi {
    @GET("planetary/apod")
    suspend fun fetchApodAsync(@QueryMap param: Map<String, String>): Response<Apod>
}