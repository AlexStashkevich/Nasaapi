package by.psu.nasaapi.model

import retrofit2.Response

sealed class ApiResponse<T> {
    data class Success<T>(val data: T): ApiResponse<T>()
    class SuccessEmpty<T>: ApiResponse<T>()
    data class Error<T>(val errorMessage: String): ApiResponse<T>()

    companion object {
        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if(response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    SuccessEmpty()
                } else {
                    Success(body)
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMessage = if(msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                Error(errorMessage ?: "Unknown error")
            }
        }
    }
}