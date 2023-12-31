package ai.hara.ureshii.util.network

import retrofit2.Response

sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            val connectionError = ErrorObject(2000, error.localizedMessage)
            return ApiErrorResponse(connectionError)
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null) {
                    ApiNullResponse()
                } else {
                    ApiSuccessResponse(body = body)
                }
            } else {
                val msg = response.errorBody()!!.string()
                val errorMsg = msg.ifEmpty {
                    response.message()
                }
                ApiErrorResponse(ErrorObject(response.code(), errorMsg))
            }
        }
    }
}

/**
 * separate class for HTTP Body Null resposes so that we can make ApiSuccessResponse's body null.
 */
class ApiNullResponse<T> : ApiResponse<T>()

/**
 * separate class for HTTP 200 resposes so that we can make ApiSuccessResponse's body has elements.
 */
data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

/**
 * separate class for 2000 code : connection failed.
 */
data class ApiErrorResponse<T>(val error: ErrorObject) : ApiResponse<T>()