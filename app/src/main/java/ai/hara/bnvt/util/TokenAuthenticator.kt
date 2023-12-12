package ai.hara.bnvt.util

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.io.IOException

class TokenAuthenticator : Authenticator {

    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            //MusicService.service.tokenExpired.postValue(401)
        }
        return null
    }
}