package by.psu.nasaapi.repository

import by.psu.nasaapi.model.ApiResponse
import retrofit2.Response

open class BaseRepository{

    suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>) : T?{

        return when(val result = safeApiResponse(call)) {
            is ApiResponse.Success -> {
                result.data
            }
            else -> null
        }
    }

    private suspend fun <T : Any> safeApiResponse(call: suspend () -> Response<T>): ApiResponse<T> {
        val response = call.invoke()

        return when {
            response.isSuccessful -> {
                when (val body = response.body()) {
                    null -> ApiResponse.Error("empty response")
                    else -> ApiResponse.Success(body)
                }
            }
            else -> {
                val msg = response.errorBody()?.string()
                val errorMsg = when {
                    msg.isNullOrEmpty() -> response.message()
                    else -> msg
                }
                ApiResponse.Error(errorMsg ?: "unknown error")
            }
        }
    }
}