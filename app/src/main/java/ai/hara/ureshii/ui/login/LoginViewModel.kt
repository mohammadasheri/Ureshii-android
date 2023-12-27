package ai.hara.ureshii.ui.login

import ai.hara.ureshii.data.repository.UserRepository
import ai.hara.ureshii.util.network.ResultWrapper
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: UserRepository,
    private val shp: SharedPreferences
) : ViewModel() {

    val isLoggedIn = mutableStateOf(false)

    init {
        viewModelScope.launch {
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            when (val response = repository.registerUser(username, password)) {
                is ResultWrapper.NetworkError -> Timber.tag("Mohamamd").i(response.error)
                is ResultWrapper.Error -> Timber.tag("Mohamamd").i(response.error.toString())
                is ResultWrapper.Success -> Timber.tag("Mohamamd").i(response.value.toString())
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            when (val response = repository.registerUser(username, password)) {
                is ResultWrapper.NetworkError -> isLoggedIn.value = false
                is ResultWrapper.Error -> isLoggedIn.value = false
                is ResultWrapper.Success -> isLoggedIn.value = true
            }
        }
    }

    fun saveToken(token: String) {
        with(shp.edit()) {
            putString("token", token)
            apply()
        }
    }
}