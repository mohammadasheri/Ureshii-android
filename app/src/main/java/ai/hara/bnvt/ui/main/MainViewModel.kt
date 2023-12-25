package ai.hara.bnvt.ui.main

import ai.hara.bnvt.data.model.Song
import ai.hara.bnvt.service.PlayerEvent
import ai.hara.bnvt.service.SimpleMediaServiceHandler
import ai.hara.bnvt.service.SimpleMediaState
import ai.hara.bnvt.util.getHostURL
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val simpleMediaServiceHandler: SimpleMediaServiceHandler,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var duration by savedStateHandle.saveable { mutableStateOf(0L) }
    var index by savedStateHandle.saveable { mutableStateOf(0) }
    val selectedSong = MutableLiveData<Song>()
    var progress by savedStateHandle.saveable { mutableStateOf(0f) }
    var progressString by savedStateHandle.saveable { mutableStateOf("00:00") }
    var isPlaying by savedStateHandle.saveable { mutableStateOf(false) }
    var isLoggedOut = MutableLiveData(false)
    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()
    private val _uiState = MutableStateFlow<UIState>(UIState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        simpleMediaServiceHandler
        viewModelScope.launch {
            delay(200)
            _loading.value = false
            simpleMediaServiceHandler.simpleMediaState.collect { mediaState ->
                when (mediaState) {
                    is SimpleMediaState.Buffering -> calculateProgressValues(mediaState.progress)
                    SimpleMediaState.Initial -> _uiState.value = UIState.Initial
                    is SimpleMediaState.Playing -> isPlaying = mediaState.isPlaying
                    is SimpleMediaState.Progress -> calculateProgressValues(mediaState.progress)
                    is SimpleMediaState.Ready -> {
                        duration = mediaState.duration
                        _uiState.value = UIState.Ready
                    }
                }
            }
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Stop)
        }
    }

    fun onUIEvent(uiEvent: UIEvent) = viewModelScope.launch {
        when (uiEvent) {
            UIEvent.Backward -> simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Backward)
            UIEvent.Forward -> simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Forward)
            UIEvent.PlayPause -> simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.PlayPause)
            is UIEvent.UpdateProgress -> {
                progress = uiEvent.newProgress
                simpleMediaServiceHandler.onPlayerEvent(
                    PlayerEvent.UpdateProgress(
                        uiEvent.newProgress
                    )
                )
            }
        }
    }

    fun formatDuration(duration: Long): String {
        val minutes: Long = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds: Long = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)
                - minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun calculateProgressValues(currentProgress: Long) {
        progress = if (currentProgress > 0) (currentProgress.toFloat() / duration) else 0f
        progressString = formatDuration(currentProgress)
    }

    fun loadData(songs: List<Song>, startIndex: Int) {
        selectedSong.value = songs[index]
        val mediaItemList = mutableListOf<MediaItem>()
        songs.forEach { song ->
            mediaItemList.add(
                MediaItem.Builder()
                    .setUri("${getHostURL()}song/download/${song.id}")
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setFolderType(MediaMetadata.FOLDER_TYPE_ALBUMS)
                            .setArtworkUri(Uri.parse("https://cdns-images.dzcdn.net/images/cover/1fddc1ab0535ee34189dc4c9f5f87bf9/264x264.jpg"))
                            .setAlbumTitle(song.album)
                            .setDisplayTitle(song.name)
                            .build()
                    ).build()
            )
        }
        simpleMediaServiceHandler.addMediaItemList(mediaItemList, startIndex, 0)
    }

//    private fun loadData() {
//        val mediaItemList = mutableListOf<MediaItem>()
//        (1..17).forEach {
//            mediaItemList.add(
//                MediaItem.Builder()
//                    .setUri("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-$it.mp3")
//                    .setMediaMetadata(
//                        MediaMetadata.Builder()
//                            .setFolderType(MediaMetadata.FOLDER_TYPE_ALBUMS)
//                            .setArtworkUri(Uri.parse("https://cdns-images.dzcdn.net/images/cover/1fddc1ab0535ee34189dc4c9f5f87bf9/264x264.jpg"))
//                            .setAlbumTitle("SoundHelix")
//                            .setDisplayTitle("Song $it")
//                            .build()
//                    ).build()
//            )
//        }
//        simpleMediaServiceHandler.addMediaItemList(mediaItemList)
//    }
}

sealed class UIEvent {
    object PlayPause : UIEvent()
    object Backward : UIEvent()
    object Forward : UIEvent()
    data class UpdateProgress(val newProgress: Float) : UIEvent()
}

sealed class UIState {
    object Initial : UIState()
    object Ready : UIState()
}