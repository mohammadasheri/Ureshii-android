package ai.hara.bnvt.data.model

import com.google.gson.annotations.Expose

data class RegisterUserResponse(@Expose val id: Long, @Expose val username: String)
