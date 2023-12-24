package ai.hara.bnvt.data.repository


import ai.hara.bnvt.data.model.Playlist
import ai.hara.bnvt.data.service.PlaylistService
import ai.hara.bnvt.util.AppExecutors
import ai.hara.bnvt.util.network.ApiResponse
import ai.hara.bnvt.util.network.NetworkBoundResource
import ai.hara.bnvt.util.network.Resource
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
