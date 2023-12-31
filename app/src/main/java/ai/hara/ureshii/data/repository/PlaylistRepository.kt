package ai.hara.ureshii.data.repository


import ai.hara.ureshii.data.model.Playlist
import ai.hara.ureshii.data.service.PlaylistService
import ai.hara.ureshii.util.network.NetworkHelper
import ai.hara.ureshii.util.network.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class PlaylistRepository(
    private val service: PlaylistService, private val networkHelper: NetworkHelper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getHomePlaylists(): ResultWrapper<List<Playlist>> {
        return networkHelper.safeApiCall(dispatcher) {
            service.getPlaylists()
        }
    }
}
