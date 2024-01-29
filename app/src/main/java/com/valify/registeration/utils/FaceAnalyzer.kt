package com.valify.registeration.utils

import android.media.Image
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceDetectorOptions.CLASSIFICATION_MODE_ALL
import com.google.mlkit.vision.face.FaceDetectorOptions.CONTOUR_MODE_ALL
import com.google.mlkit.vision.face.FaceDetectorOptions.LANDMARK_MODE_NONE
import com.google.mlkit.vision.face.FaceDetectorOptions.PERFORMANCE_MODE_FAST
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FaceAnalyzer(private val callBack: FaceAnalyzerCallback) : ImageAnalysis.Analyzer {

    companion object {
        const val THROTTLE_TIMEOUT_MS = 1_000L
    }

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val realTimeOpts = FaceDetectorOptions.Builder()
        .setContourMode(CONTOUR_MODE_ALL)// Whether to detect the contours of facial features. Contours are detected for only the most prominent face in an image
        .setPerformanceMode(PERFORMANCE_MODE_FAST)//Favor speed or accuracy when detecting faces.
        .setLandmarkMode(LANDMARK_MODE_NONE) //Whether to attempt to identify facial “landmarks”: eyes, ears, nose, cheeks, mouth, and so on
        .setClassificationMode(CLASSIFICATION_MODE_ALL) //for Whether or not to classify faces into categories such as “smiling”, and “eyes open”
        .setMinFaceSize(0.20f)//Sets the smallest desired face size, expressed as the ratio of the width of the head to width of the image
        .build()

    private val detector = FaceDetection.getClient(realTimeOpts)

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        scope.launch {
            val mediaImage: Image = imageProxy.image ?: run { imageProxy.close(); return@launch }
            val inputImage =
                InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            suspendCoroutine { continuation ->
                detector.process(inputImage)
                    .addOnSuccessListener { faces ->
                        for (face in faces) {
                            if (face.smilingProbability != null) {
                                if ((face.smilingProbability ?: 0.0f) >= 0.3f) {
                                    callBack.processFace(faces)
                                    imageProxy.close()
                                }
                            }
                        }
                    }
                    .addOnFailureListener {
                        callBack.errorFace(it.message.orEmpty())
                        imageProxy.close()
                    }
                    .addOnCompleteListener {
                        continuation.resume(Unit)
                        imageProxy.close()
                    }
            }
            delay(THROTTLE_TIMEOUT_MS)
        }.invokeOnCompletion { exception ->
            exception?.printStackTrace()
            imageProxy.close()
        }
    }
}

interface FaceAnalyzerCallback {
    fun processFace(faces: List<Face>)
    fun errorFace(error: String)
}