package ai.hara.bnvt.data.model

import com.google.gson.annotations.Expose

data class RegisterUserRequest(@Expose val username: String, @Expose val password: String)
