package ai.hara.ureshii.ui.home

import ai.hara.ureshii.data.model.Playlist
import ai.hara.ureshii.data.model.Song
import ai.hara.ureshii.data.repository.PlaylistRepository
import ai.hara.ureshii.data.repository.SongRepository
import ai.hara.ureshii.util.network.Resource
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var repository: SongRepository,
    private var playlistRepository: PlaylistRepository
) : ViewModel() {

    var songs = mutableStateListOf<Song>()
    var playlists = mutableStateListOf<Playlist>()

    fun getSongs(): LiveData<Resource<List<Song>>> = repository.getSongs()
    fun getPlaylists(): LiveData<Resource<List<Playlist>>> = playlistRepository.getHomePlaylists()
}