package ai.hara.ureshii.ui.home

import ai.hara.ureshii.data.model.Playlist
import ai.hara.ureshii.data.model.Song
import ai.hara.ureshii.data.repository.PlaylistRepository
import ai.hara.ureshii.data.repository.SongRepository
import ai.hara.ureshii.util.network.ResultWrapper
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
@OptIn(SavedStateHandleSaveableApi::class)
class HomeViewModel @Inject constructor(
    private var repository: SongRepository,
    private var playlistRepository: PlaylistRepository,
    savedStateHandle: SavedStateHandle

) : ViewModel() {

    var songs = mutableStateListOf<Song>()
    var playlists = mutableStateListOf<Playlist>()
    var isLoggedIn by savedStateHandle.saveable { mutableStateOf(true) }

    fun getSongs() {
        viewModelScope.launch {
            when (val response = repository.getSongs()) {
                is ResultWrapper.Error -> Timber.tag("Mohamamd").i(response.error.toString())
                is ResultWrapper.Success -> {
                    songs.clear()
                    songs.addAll(response.value)
                    Timber.tag("Mohamamd").i(response.value.toString())
                }
                is ResultWrapper.AuthorizationError -> isLoggedIn = false
                is ResultWrapper.NetworkError -> isLoggedIn = false
            }

            when (val response = playlistRepository.getHomePlaylists()) {
                is ResultWrapper.Error -> Timber.tag("Mohamamd").i(response.error.toString())
                is ResultWrapper.Success -> {
                    playlists.clear()
                    playlists.addAll(response.value)
                }
                is ResultWrapper.AuthorizationError -> isLoggedIn = false
                is ResultWrapper.NetworkError -> Timber.tag("Mohamamd").i(response.error)
            }
        }
    }
}