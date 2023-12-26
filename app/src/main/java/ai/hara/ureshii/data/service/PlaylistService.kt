package ai.hara.ureshii.data.service

import ai.hara.ureshii.data.model.Playlist
import ai.hara.ureshii.util.enums.HTTPVerb
import ai.hara.ureshii.util.network.ApiResponse
import androidx.lifecycle.LiveData
import retrofit2.http.HTTP

interface PlaylistService {
    @HTTP(method = HTTPVerb.GET, path = "playlist/list", hasBody = false)
    fun getPlaylists(): LiveData<ApiResponse<List<Playlist>>>
}
