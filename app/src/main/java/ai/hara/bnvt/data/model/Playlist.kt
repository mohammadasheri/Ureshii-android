package ai.hara.bnvt.data.model

import com.google.gson.annotations.Expose

data class Playlist(
        @Expose var id: Int = 0,
        @Expose var name: String = "",
        @Expose var tracks: List<Int>? = null
)
