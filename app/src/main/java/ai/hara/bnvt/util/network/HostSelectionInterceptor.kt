package ai.hara.bnvt.util.network

import ai.hara.bnvt.util.getRandomHost
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class HostSelectionInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()


        request = changeReq(request)


        var response: Response = Response.Builder().request(request).protocol(Protocol.HTTP_1_1).message("عدم دسترسی به اینترنت").code(HttpURLConnection.HTTP_BAD_GATEWAY)
                .body("عدم دسترسی به اینترنت".toResponseBody()).build()

        try {
            response = chain.proceed(request)
            if (response.code >= 500) {
                if (response.request.url.host != URL(getRandomHost()).host) {
                    return intercept(chain)
                }
                return response
            }

        } catch (e: Throwable) {
            if (response.request.url.host != URL(getRandomHost()).host) {

                return intercept(chain)
            }
            return response
        }
        return response
    }

    private fun changeReq(request: Request): Request {

        val aa = URL(getRandomHost())
        val newUrl = request.url.newBuilder()
                .host(aa.host)

                .build()
        return request.newBuilder()
                .url(newUrl)
                .build()
    }

}