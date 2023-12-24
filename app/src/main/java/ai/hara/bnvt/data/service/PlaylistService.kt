package ai.hara.bnvt.data.service

import ai.hara.bnvt.data.model.Playlist
import ai.hara.bnvt.data.model.Song
import ai.hara.bnvt.util.enums.HTTPVerb
import ai.hara.bnvt.util.network.ApiResponse
import androidx.lifecycle.LiveData
import retrofit2.http.HTTP

interface PlaylistService {
    @HTTP(method = HTTPVerb.GET, path = "playlist/list", hasBody = false)
    fun getPlaylists(): LiveData<ApiResponse<List<Playlist>>>
}
