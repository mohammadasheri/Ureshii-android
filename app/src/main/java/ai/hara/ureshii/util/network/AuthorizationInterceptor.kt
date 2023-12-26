package ai.hara.ureshii.util.network

import android.content.SharedPreferences
import okhttp3.*
import java.io.IOException

class AuthorizationInterceptor(private val sharedPrefrence: SharedPreferences) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPrefrence.getString("token", "")
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}