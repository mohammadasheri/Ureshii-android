package ai.hara.ureshii.data.model

import com.google.gson.annotations.Expose

data class Song(
    @Expose var id: Int = 0,
    @Expose var name: String = "",
    @Expose var songMediaType: String = "",
    @Expose var pictureMediaType: String = "",
    @Expose var language: String = "",
    @Expose var duration: Long = 0,
    @Expose var likes: Long = 0,
    @Expose var playCount: Long = 0,
    @Expose var album: String? = null,
    @Expose var artist: List<Artist>,
)