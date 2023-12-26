package ai.hara.ureshii.data.model

import com.google.gson.annotations.Expose

data class SearchResult(@Expose var tracks: List<Song>, @Expose var playlists: List<Playlist>)
