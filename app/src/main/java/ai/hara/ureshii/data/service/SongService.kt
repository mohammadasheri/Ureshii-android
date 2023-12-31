package ai.hara.ureshii.data.service

import ai.hara.ureshii.data.model.SearchResult
import ai.hara.ureshii.data.model.Song
import ai.hara.ureshii.util.enums.HTTPVerb
import ai.hara.ureshii.util.network.ApiResponse
import androidx.lifecycle.LiveData
import retrofit2.http.*

interface SongService {

    @HTTP(method = HTTPVerb.GET, path = "playlist/track", hasBody = false)
    fun getPlaylistSongs(
        @Header("TK") token: String,
        @Header("UUID") uuid: String,
        @Header("DEV") dev: String,
        @Query("playlist") playlistId: Int
    ): LiveData<ApiResponse<List<Song>>>

    @HTTP(method = HTTPVerb.POST, path = "favorite", hasBody = true)
    fun likeSong(
        @Header("TK") token: String, @Query("track") songId: Int, @Body dummy: Any
    )
            : LiveData<ApiResponse<SearchResult>>


    @HTTP(method = HTTPVerb.DELETE, path = "favorite", hasBody = false)
    fun dislikeSong(
        @Header("TK") token: String,
        @Header("UUID") uuid: String,
        @Header("DEV") dev: String,
        @Query("track") songId: Int
    )
            : LiveData<ApiResponse<SearchResult>>

    @HTTP(method = HTTPVerb.GET, path = "song/list", hasBody = false)
    suspend fun getSongs(): List<Song>
}
