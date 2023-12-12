//package ai.hara.bnvt.util
//
//import ai.hara.bnvt.R
//import ai.hara.bnvt.data.model.gsonModel.Song
////import ai.hara.bnvt.media.MusicService
//import ai.hara.bnvt.util.keystore.ApplicationPreferences
//import android.content.Context
//import android.content.SharedPreferences
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//
//val sharedPref: SharedPreferences = MyApplication.appcontext.getSharedPreferences(
//    MyApplication.appcontext.getString(
//        R.string
//            .preference_file_key
//    ), Context.MODE_PRIVATE
//)
//
//val keyStoreHandler: ApplicationPreferences =
//    ApplicationPreferences.getInstance(MyApplication.appcontext)
//
//fun saveCredentials(TK: String) {
////    MusicService.service.updateToken(TK)
//
//    keyStoreHandler.saveData(TK, MyApplication.appcontext.getString(R.string.token_key))
//    keyStoreHandler.saveData(
//        TK.split("-")[0],
//        MyApplication.appcontext.getString(R.string.user_key)
//    )
//
//    with(sharedPref.edit()) {
//        putBoolean(MyApplication.appcontext.getString(R.string.is_login_key), true)
//        commit()
//    }
//}
//
//fun getId(): Int {
//    return keyStoreHandler.getLastData(MyApplication.appcontext.getString(R.string.user_key), "0")
//        .toInt()
//}
//
//fun getLastSongs(): List<Song>? {
//
//    with(sharedPref) {
//        val isVisibleSong = getBoolean("last_song_visible", false)
//        if (!isVisibleSong)
//            return null
//        return Gson().fromJson<List<Song>>(getString("last_songs", null),
//            object : TypeToken<List<Song>>() {}.type)
//
//    }
//}
//
//fun getLastSongId(): String? {
//
//    with(sharedPref) {
//        val isVisibleSong = getBoolean("last_song_visible", false)
//        if (!isVisibleSong)
//            return null
//        return getString("last_song_id", null)
//
//    }
//}
//
//
//fun saveLastSongPlayed(nowPlayingMetadata: MutableList<Song>?, id: String?) {
//    with(sharedPref.edit()) {
//
//        if (nowPlayingMetadata == null) {
//            putBoolean("last_song_visible", false)
//
//        } else {
//            putString("last_song_id", id)
//            putString("last_songs", Gson().toJson(nowPlayingMetadata))
//        }
//        commit()
//    }
//}
//
//fun getToken(): String {
//    val token: String = MyApplication.appcontext.getString(R.string.default_token)
//
//    return keyStoreHandler.getLastData(
//        MyApplication.appcontext.getString(R.string.token_key),
//        token
//    )
//
//}
//
//fun savePlayInfo(title: String, subtitle: String) {
//    with(sharedPref.edit()) {
//        putString(MyApplication.appcontext.getString(R.string.play_title_key), title)
//        putString(MyApplication.appcontext.getString(R.string.play_subtitle_key), subtitle)
//        commit()
//    }
//}
//
//fun getPlayInfo(): Pair<String?, String?> {
//    with(sharedPref) {
//        val title = getString(MyApplication.appcontext.getString(R.string.play_title_key), "")
//        val subtitle = getString(MyApplication.appcontext.getString(R.string.play_subtitle_key), "")
//        return Pair(title, subtitle)
//    }
//}
//
//fun saveCurrentSong(songId: String) {
//    with(sharedPref.edit()) {
//        putString(MyApplication.appcontext.getString(R.string.current_song_key), songId)
//        commit()
//    }
//}
//
//fun getCurrentSong(): String {
//    with(sharedPref) {
//        return getString(MyApplication.appcontext.getString(R.string.current_song_key), "-1")
//            ?: "-1"
//    }
//}
//
//fun getPlayQuality(): Boolean {
//    with(sharedPref) {
//        return getBoolean(MyApplication.appcontext.getString(R.string.high_quality_key), false)
//    }
//}
//
//fun setPlayQuality(highQuality: Boolean) {
//    with(sharedPref.edit()) {
//        putBoolean(MyApplication.appcontext.getString(R.string.high_quality_key), highQuality)
//        commit()
//    }
//
//}
//
//fun getFCMToken(): String {
//    with(sharedPref) {
//
//        return getString(MyApplication.appcontext.getString(R.string.firebase_key), "null")
//            ?: "null"
//    }
//}