package ai.hara.ureshii.util.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class NetworkHelper {
    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError(throwable.toString())
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = ErrorObject(
                            code,
                            throwable.response()?.errorBody()?.source().toString()
                        )
                        if (code == 401) {
                            ResultWrapper.AuthorizationError(code, errorResponse)
                        } else {
                            ResultWrapper.Error(code, errorResponse)
                        }
                    }

                    else -> {
                        ResultWrapper.Error(null, null)
                    }
                }
            }
        }
    }
}