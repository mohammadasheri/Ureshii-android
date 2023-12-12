package ai.hara.bnvt.data.model

import com.google.gson.annotations.Expose

data class SearchResult(@Expose var tracks: List<Song>, @Expose var playlists: List<Playlist>)
