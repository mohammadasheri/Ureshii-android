package ai.hara.ureshii.util.network

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val code: Int? = null, val error: ErrorObject? = null) :
        ResultWrapper<Nothing>()
    data class NetworkError<T>(val error: String) : ResultWrapper<T>()
}