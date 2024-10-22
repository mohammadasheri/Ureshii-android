package ai.hara.ureshii.data.service

import ai.hara.ureshii.data.model.SearchResponse
import ai.hara.ureshii.util.enums.HTTPVerb
import retrofit2.http.HTTP

interface SearchService {
    @HTTP(method = HTTPVerb.GET, path = "search", hasBody = false)
    suspend fun search(): SearchResponse
}
