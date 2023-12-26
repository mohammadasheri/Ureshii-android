package ai.hara.ureshii.data.model

import com.google.gson.annotations.Expose

data class LoginUserResponse(
    @Expose val username: String,
    @Expose val type: String,
    @Expose val token: String
)
