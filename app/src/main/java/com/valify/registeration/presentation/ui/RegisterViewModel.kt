package com.valify.registeration.presentation.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valify.registeration.data.UserRepository
import com.valify.registeration.data.User
import com.valify.registeration.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private var _onLoading by mutableStateOf(false)
    val onLoading: Boolean
        get() = _onLoading

    val userName = MutableStateFlow("")
    val phone = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    private fun saveUserData(user: User) {
        viewModelScope.launch {
            _onLoading = true
            repository.saveUser(user)
            _onLoading = false

        }
    }

}