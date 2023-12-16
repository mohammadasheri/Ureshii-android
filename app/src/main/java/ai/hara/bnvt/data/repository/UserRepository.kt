package ai.hara.bnvt.data.repository


import ai.hara.bnvt.data.model.LoginUserResponse
import ai.hara.bnvt.data.model.RegisterUserRequest
import ai.hara.bnvt.data.model.RegisterUserResponse
import ai.hara.bnvt.data.service.UserService
import ai.hara.bnvt.util.AppExecutors
import ai.hara.bnvt.util.network.ApiResponse
import ai.hara.bnvt.util.network.NetworkBoundResource
import ai.hara.bnvt.util.network.Resource
import androidx.lifecycle.LiveData

class UserRepository(private val service: UserService, private val executor: AppExecutors) {

    fun registerUser(username: String, password: String): LiveData<Resource<RegisterUserResponse>> {
        return object : NetworkBoundResource<RegisterUserResponse, RegisterUserResponse>(executor) {
            override fun createCall(): LiveData<ApiResponse<RegisterUserResponse>> {
                return service.registerUser(RegisterUserRequest(username, password))
            }
        }.asLiveData()
    }

    fun loginUser(username: String, password: String): LiveData<Resource<LoginUserResponse>> {
        return object : NetworkBoundResource<LoginUserResponse, LoginUserResponse>(executor) {
            override fun createCall(): LiveData<ApiResponse<LoginUserResponse>> {
                return service.loginUser(RegisterUserRequest(username, password))
            }
        }.asLiveData()
    }
}
