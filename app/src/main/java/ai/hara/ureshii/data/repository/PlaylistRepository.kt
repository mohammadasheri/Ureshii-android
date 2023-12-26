package ai.hara.ureshii.data.repository


import ai.hara.ureshii.data.model.Playlist
import ai.hara.ureshii.data.service.PlaylistService
import ai.hara.ureshii.util.AppExecutors
import ai.hara.ureshii.util.network.ApiResponse
import ai.hara.ureshii.util.network.NetworkBoundResource
import ai.hara.ureshii.util.network.Resource
import androidx.lifecycle.LiveData

class PlaylistRepository(private val service: PlaylistService, private val executor: AppExecutors) {
    fun getHomePlaylists(): LiveData<Resource<List<Playlist>>> {
        return object : NetworkBoundResource<List<Playlist>, List<Playlist>>(executor) {
            override fun createCall(): LiveData<ApiResponse<List<Playlist>>> {
                return service.getPlaylists()
            }
        }.asLiveData()
    }
}
