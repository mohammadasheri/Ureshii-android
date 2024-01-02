package ai.hara.ureshii.ui.playlist

import ai.hara.ureshii.data.model.Song
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
class PlaylistViewModel @Inject constructor(
    private var repository: SongRepository,
    savedStateHandle: SavedStateHandle

) : ViewModel() {

    var songs = mutableStateListOf<Song>()
    var isLoggedIn by savedStateHandle.saveable { mutableStateOf(true) }

    fun loadData(pId: Int) {
        viewModelScope.launch {
            when (val response = repository.getPlaylistSongs(pId)) {
                is ResultWrapper.Error -> Timber.tag("Mohamamd").i(response.error.toString())
                is ResultWrapper.Success -> {
                    songs.clear()
                    songs.addAll(response.value)
                }

                is ResultWrapper.AuthorizationError -> isLoggedIn = false
                is ResultWrapper.NetworkError -> Timber.tag("Mohamamd").i(response.error.toString())
            }
        }
    }
}