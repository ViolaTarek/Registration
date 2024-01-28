package com.valify.registeration.utils

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

class FaceAnalyzer(private val callBack : FaceAnalyzerCallback) : ImageAnalysis.Analyzer {

    private val realTimeOpts = FaceDetectorOptions.Builder()
        .setContourMode(CONTOUR_MODE_ALL)// Whether to detect the contours of facial features. Contours are detected for only the most prominent face in an image
        .setPerformanceMode(PERFORMANCE_MODE_FAST)//Favor speed or accuracy when detecting faces.
        .setLandmarkMode(LANDMARK_MODE_NONE) //Whether to attempt to identify facial “landmarks”: eyes, ears, nose, cheeks, mouth, and so on
        .setClassificationMode(CLASSIFICATION_MODE_ALL) //for Whether or not to classify faces into categories such as “smiling”, and “eyes open”
        .setMinFaceSize(0.20f)//Sets the smallest desired face size, expressed as the ratio of the width of the head to width of the image
        .build()

    private val detector = FaceDetection.getClient(realTimeOpts)

    @OptIn(ExperimentalGetImage::class) override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        mediaImage?.let {
            val inputImage =
                InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            detector.process(inputImage)
                .addOnSuccessListener { faces ->
                    callBack.processFace(faces)
                    imageProxy.close()
                }
                .addOnFailureListener {
                    callBack.errorFace(it.message.orEmpty())
                    imageProxy.close()
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }
}

interface FaceAnalyzerCallback {
    fun processFace(faces: List<Face>)
    fun errorFace(error: String)
}