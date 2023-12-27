package ai.hara.ureshii.ui.home

import ai.hara.ureshii.data.model.Playlist
import ai.hara.ureshii.data.model.Song
import ai.hara.ureshii.data.repository.PlaylistRepository
import ai.hara.ureshii.data.repository.SongRepository
import ai.hara.ureshii.util.network.Resource
import ai.hara.ureshii.util.network.ResultWrapper
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var repository: SongRepository,
    private var playlistRepository: PlaylistRepository
) : ViewModel() {

    var songs = mutableStateListOf<Song>()
    var playlists = mutableStateListOf<Playlist>()

    fun getSongs() {
        viewModelScope.launch {
            when (val response = repository.getSongs()) {
                is ResultWrapper.NetworkError -> Timber.tag("Mohamamd").i(response.error)
                is ResultWrapper.Error -> Timber.tag("Mohamamd").i(response.error.toString())
                is ResultWrapper.Success -> {
                    songs.clear()
                    songs.addAll(response.value)
                    Timber.tag("Mohamamd").i(response.value.toString())
                }
            }

            when (val response = playlistRepository.getHomePlaylists()) {
                is ResultWrapper.NetworkError -> Timber.tag("Mohamamd").i(response.error)
                is ResultWrapper.Error -> Timber.tag("Mohamamd").i(response.error.toString())
                is ResultWrapper.Success -> Timber.tag("Mohamamd").i(response.value.toString())
            }
        }
    }
}