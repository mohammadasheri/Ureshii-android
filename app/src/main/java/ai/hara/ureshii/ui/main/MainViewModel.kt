package ai.hara.ureshii.ui.main

import ai.hara.ureshii.data.model.Song
import ai.hara.ureshii.service.PlayerEvent
import ai.hara.ureshii.service.SimpleMediaServiceHandler
import ai.hara.ureshii.service.SimpleMediaState
import ai.hara.ureshii.util.getHostURL
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
    val selectedSong = mutableStateOf(MediaItem.Builder().build())
    val tempSelectedSongLiveData = MutableLiveData<MediaItem>()
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

                    is SimpleMediaState.TrackChange -> {
                        selectedSong.value = simpleMediaServiceHandler.getCurrentMediaItem()!!
                        tempSelectedSongLiveData.value = simpleMediaServiceHandler.getCurrentMediaItem()
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
        val mediaItemList = mutableListOf<MediaItem>()
        songs.forEach { song ->
            mediaItemList.add(
                MediaItem.Builder()
                    .setUri("${getHostURL()}song/download/${song.id}")
                    .setMediaId(song.id.toString())
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setFolderType(MediaMetadata.FOLDER_TYPE_ALBUMS)
                            .setArtworkUri(Uri.parse("${getHostURL()}song/picture/download/${song.id}"))
                            .setAlbumTitle(song.album)
                            .setDisplayTitle(song.name)
                            .setArtist(song.artist?.get(0)?.name)
                            .build()
                    ).build()
            )
        }
        simpleMediaServiceHandler.addMediaItemList(mediaItemList, startIndex, 0)
    }
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