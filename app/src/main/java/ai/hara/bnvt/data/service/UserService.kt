package ai.hara.bnvt.data.service

import ai.hara.bnvt.data.model.LoginUserResponse
import ai.hara.bnvt.data.model.RegisterUserRequest
import ai.hara.bnvt.data.model.RegisterUserResponse
import ai.hara.bnvt.util.enums.HTTPVerb
import ai.hara.bnvt.util.network.ApiResponse
import androidx.lifecycle.LiveData
import retrofit2.http.Body
import retrofit2.http.HTTP

interface UserService {

    @HTTP(method = HTTPVerb.POST, path = "user/create", hasBody = true)
    fun registerUser(@Body body: RegisterUserRequest): LiveData<ApiResponse<RegisterUserResponse>>

    @HTTP(method = HTTPVerb.POST, path = "user/login", hasBody = true)
    fun loginUser(@Body body: RegisterUserRequest): LiveData<ApiResponse<LoginUserResponse>>
}
