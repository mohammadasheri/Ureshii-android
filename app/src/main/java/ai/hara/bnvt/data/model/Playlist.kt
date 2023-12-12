package ai.hara.bnvt.data.model

import com.google.gson.annotations.Expose

data class Playlist(
        @Expose var id: Int = 0,
        @Expose var image: String? = null,
        @Expose var public: Boolean = true,
        @Expose var username: String? = null,
        @Expose var user: Int = 0,
        @Expose var title: String = "",
        @Expose var thumbnail: String? = null,
        @Expose var tracks: List<Int>? = null
)
