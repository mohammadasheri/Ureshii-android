package ai.hara.ureshii.data.repository


import ai.hara.ureshii.data.model.SearchResponse
import ai.hara.ureshii.data.service.SearchService
import ai.hara.ureshii.util.network.NetworkHelper
import ai.hara.ureshii.util.network.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SearchRepository(
    private val service: SearchService,
    private val networkHelper: NetworkHelper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun search(): ResultWrapper<SearchResponse> {
        return networkHelper.safeApiCall(dispatcher) {
            service.search()
        }
    }
}
