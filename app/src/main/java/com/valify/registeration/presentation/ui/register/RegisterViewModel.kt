package com.valify.registeration.presentation.ui.register

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valify.registeration.data.UserRepository
import com.valify.registeration.data.User
import com.valify.registeration.domain.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userCase: UserUseCase) : ViewModel() {

    private val _userName: MutableStateFlow<String> = MutableStateFlow("")
    val userName: StateFlow<String> get() = _userName

    private val _email: MutableStateFlow<String> = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _phone: MutableStateFlow<String> = MutableStateFlow("")
    val phone: StateFlow<String> get() = _phone

    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    var registerDone by mutableStateOf(false)
    var error by mutableStateOf("")

    fun saveUserData() {
        val user = User(
            userName = userName.value,
            email = email.value,
            phone = phone.value,
            password = password.value
        )
        viewModelScope.launch {
            userCase.saveUser(user)
            registerDone = true
        }
    }

    fun resetError() {
        error = ""
    }

    fun validateData() {
        val regexString = "[a-zA-Z]+"
        val phoneRegex = "^[0-9]*"
        if (_userName.value.matches(regexString.toRegex()).not()) {
            error = "Please Enter Valid user name"
            return
        }
        if (isEmailValid(_email.value).not()) {
            error = "Please Enter Valid Email"
            return
        }
        if (_phone.value.isEmpty()) {
            error = "Please Enter Valid Phone number"
            return
        }
        if (_password.value.isEmpty()) {
            error = "Please Enter valid password"
            return
        }
        if (_password.value.length < 6) {
            error = "Please Enter valid password larger than 6 digits"
            return
        }
        saveUserData()
    }

    fun setName(name: String) {
        _userName.value = name

    }

    fun setEmail(emailText: String) {
        _email.value = emailText
    }

    fun setPhone(phoneText: String) {
        _phone.value = phoneText
    }

    fun setPassword(pass: String) {
        _password.value = pass
    }

    fun isEmailValid(emailAddress: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
    }


}



