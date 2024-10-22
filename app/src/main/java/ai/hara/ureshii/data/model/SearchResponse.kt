package ai.hara.ureshii.data.model

import com.google.gson.annotations.Expose

data class SearchResponse(
        @Expose var playlists: List<Playlist>? = null,
        @Expose var songs: List<Song>? = null
)
