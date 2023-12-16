package ai.hara.bnvt.ui.login

import ai.hara.bnvt.data.model.LoginUserResponse
import ai.hara.bnvt.data.model.RegisterUserResponse
import ai.hara.bnvt.data.repository.UserRepository
import ai.hara.bnvt.util.network.Resource
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val repository: UserRepository,private val shp: SharedPreferences) : ViewModel() {

    init {
        viewModelScope.launch {
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
        }
    }

    fun register(username: String, password: String): LiveData<Resource<RegisterUserResponse>> {
        return repository.registerUser(username, password)
    }

    fun login(username: String, password: String): LiveData<Resource<LoginUserResponse>> {
        return repository.loginUser(username, password)
    }

    fun saveToken(token:String) {
        with (shp.edit()) {
            putString("token", token)
            apply()
        }
    }
}