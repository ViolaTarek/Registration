package com.valify.smileregisteration.presentation.ui.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import android.util.Base64
import android.util.Base64.DEFAULT
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.TypeConverter
import com.google.mlkit.vision.face.Face
import com.valify.smileregisteration.data.UserRepository
import com.valify.smileregisteration.domain.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor( private val userCase: UserUseCase) : ViewModel() {

    var captureDone by mutableStateOf(false)
    var data by mutableStateOf("")
    fun saveImage(image: ImageProxy) {

        viewModelScope.launch {
            val imageToSave = bitmapToBase64(image.toBitmap())
            val user = userCase.getCurrentUser()
            data = imageToSave
            user.copy(image=imageToSave)
           userCase.updateUser(user)
            data = userCase.getCurrentUser().image.toString()
            captureDone = true
        }
    }
    @TypeConverter
    fun bitmapToBase64(bitmap: Bitmap) : String{
        // create a ByteBuffer and allocate size equal to bytes in   the bitmap
        val byteBuffer = ByteBuffer.allocate(bitmap.height * bitmap.rowBytes)
        //copy all the pixels from bitmap to byteBuffer
        bitmap.copyPixelsToBuffer(byteBuffer)
        //convert byte buffer into byteArray
        val byteArray = byteBuffer.array()
        //convert byteArray to Base64 String with default flags
        return Base64.encodeToString(byteArray, DEFAULT)
    }
}