package ai.hara.ureshii.data.model

import com.google.gson.annotations.Expose

data class Song(
    @Expose var id: Int = 0,
    @Expose var album: String? = null,
    @Expose var artist: List<Artist>? = null,
    @Expose var duration: Long = 0,
    @Expose var format_type: String = "",
    @Expose var genre: String? = null,
    @Expose var image: String? = null,
    @Expose var thumbnail: String? = null,
    @Expose var name: String = "",
    @Expose var views: Long = 0,
    @Expose var user: Int = 0
)