package ai.hara.bnvt.ui.home

import ai.hara.bnvt.data.model.Song
import ai.hara.bnvt.data.repository.SongRepository
import ai.hara.bnvt.util.network.Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private var repository: SongRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getSongs(): LiveData<Resource<List<Song>>> = repository.getSongs()
}