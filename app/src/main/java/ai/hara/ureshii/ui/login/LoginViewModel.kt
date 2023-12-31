package ai.hara.ureshii.ui.login

import ai.hara.ureshii.data.repository.UserRepository
import ai.hara.ureshii.util.network.ResultWrapper
import android.content.SharedPreferences
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

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: UserRepository,
    private val shp: SharedPreferences,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var isLoggedIn by savedStateHandle.saveable { mutableStateOf(false) }

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
                is ResultWrapper.AuthorizationError -> TODO()
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            when (val response = repository.loginUser(username, password)) {
                is ResultWrapper.NetworkError -> isLoggedIn = false
                is ResultWrapper.Error -> isLoggedIn = false
                is ResultWrapper.Success -> {
                    isLoggedIn = true
                    saveToken(response.value.token)
                }
                is ResultWrapper.AuthorizationError -> TODO()
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