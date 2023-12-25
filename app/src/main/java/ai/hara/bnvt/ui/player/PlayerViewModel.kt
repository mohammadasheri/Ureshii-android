package ai.hara.bnvt.ui.player

import ai.hara.bnvt.data.model.Playlist
import ai.hara.bnvt.data.model.Song
import ai.hara.bnvt.data.repository.PlaylistRepository
import ai.hara.bnvt.data.repository.SongRepository
import ai.hara.bnvt.util.network.Resource
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private var repository: SongRepository,
    private var playlistRepository: PlaylistRepository
) : ViewModel() {

    var songs = mutableStateListOf<Song>()
    var playlists = mutableStateListOf<Playlist>()

    fun getSongs(): LiveData<Resource<List<Song>>> = repository.getSongs()
    fun getPlaylists(): LiveData<Resource<List<Playlist>>> = playlistRepository.getHomePlaylists()
}