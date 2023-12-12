package ai.hara.bnvt.util.network

import com.google.gson.annotations.Expose

data class ErrorObject(@Expose val code: Int, @Expose val message: String)