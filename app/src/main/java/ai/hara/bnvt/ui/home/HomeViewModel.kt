package ai.hara.bnvt.ui.home

import ai.hara.bnvt.data.model.Song
import ai.hara.bnvt.data.repository.SongRepository
import ai.hara.bnvt.util.network.Resource
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private var repository: SongRepository, private val sh: SharedPreferences) : ViewModel() {

    var songs = mutableStateListOf<Song>()
    fun getSongs(): LiveData<Resource<List<Song>>> = repository.getSongs()
}