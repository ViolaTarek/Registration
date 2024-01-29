package com.valify.registeration.presentation.ui.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.face.Face
import com.valify.registeration.data.UserRepository
import com.valify.registeration.domain.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor( private val userCase: UserUseCase) : ViewModel() {

    var captureDone by mutableStateOf(false)
    var data by mutableStateOf("")
    fun saveImage(image: ImageProxy) {
        val imageToSave = image.toBitmap()
        viewModelScope.launch {
            val user = userCase.getCurrentUser()
            user.copy(image=imageToSave.toString())
            userCase.saveUser(user)
            data = userCase.getCurrentUser().userName
            captureDone = true
        }
    }

}