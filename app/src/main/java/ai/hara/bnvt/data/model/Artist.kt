package ai.hara.bnvt.data.model

import com.google.gson.annotations.Expose

data class Artist(
    @Expose  var id: Int = 0,
    @Expose  var name: String
)