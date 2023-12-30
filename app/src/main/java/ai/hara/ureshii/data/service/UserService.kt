package ai.hara.ureshii.data.service

import ai.hara.ureshii.data.model.LoginUserResponse
import ai.hara.ureshii.data.model.RegisterUserRequest
import ai.hara.ureshii.data.model.RegisterUserResponse
import ai.hara.ureshii.util.enums.HTTPVerb
import ai.hara.ureshii.util.network.ApiResponse
import androidx.lifecycle.LiveData
import retrofit2.http.Body
import retrofit2.http.HTTP

interface UserService {

    @HTTP(method = HTTPVerb.POST, path = "user/create", hasBody = true)
    suspend fun registerUser(@Body body: RegisterUserRequest): RegisterUserResponse

    @HTTP(method = HTTPVerb.POST, path = "user/login", hasBody = true)
    suspend fun loginUser(@Body body: RegisterUserRequest): LoginUserResponse
}
