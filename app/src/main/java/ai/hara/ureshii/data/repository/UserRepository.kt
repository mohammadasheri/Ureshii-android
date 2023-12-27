package ai.hara.ureshii.data.repository


import ai.hara.ureshii.data.model.LoginUserResponse
import ai.hara.ureshii.data.model.RegisterUserRequest
import ai.hara.ureshii.data.model.RegisterUserResponse
import ai.hara.ureshii.data.service.UserService
import ai.hara.ureshii.util.network.NetworkHelper
import ai.hara.ureshii.util.network.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class UserRepository(
    private val service: UserService, private val networkHelper: NetworkHelper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun registerUser(
        username: String,
        password: String
    ): ResultWrapper<RegisterUserResponse> {
        return networkHelper.safeApiCall(dispatcher) {
            service.registerUser(RegisterUserRequest(username, password))
        }
    }

    suspend fun loginUser(username: String, password: String): ResultWrapper<LoginUserResponse> {
        return networkHelper.safeApiCall(dispatcher) {
            service.loginUser(RegisterUserRequest(username, password))
        }
    }
}
