package com.valify.registeration.presentation.ui.camera.composable

import android.content.Context
import android.util.Size
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.Toast
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.mlkit.vision.face.Face
import com.valify.registeration.presentation.ui.camera.CameraViewModel
import com.valify.registeration.utils.FaceAnalyzer
import com.valify.registeration.utils.FaceAnalyzerCallback

@Composable
fun CameraScreenContent(viewModel: CameraViewModel) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context) }

    AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
        PreviewView(context).apply {
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            setBackgroundColor(android.graphics.Color.BLACK)
            scaleType = PreviewView.ScaleType.FILL_START

        }.also { previewView ->
            startSmileRecognition(viewModel, context, cameraController, lifecycleOwner, previewView)
        }
    })
}

private fun startSmileRecognition(
    viewModel: CameraViewModel,
    context: Context,
    cameraController: LifecycleCameraController,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView
) {
    val cameraSelectorFront: CameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
        .build()

    cameraController.imageAnalysisTargetSize = CameraController.OutputSize(AspectRatio.RATIO_16_9)
    cameraController.setImageAnalysisAnalyzer(
        ContextCompat.getMainExecutor(context),
        FaceAnalyzer(object : FaceAnalyzerCallback {
            override fun processFace(faces: List<Face>) {
                 val mainExecutor = ContextCompat.getMainExecutor(context)
                cameraController.takePicture(mainExecutor,object : ImageCapture.OnImageCapturedCallback(){
                    override fun onCaptureSuccess(image: ImageProxy) {
                        viewModel.saveImage(image)

                    }
                })

            }

            override fun errorFace(error: String) {
                mToast(context,"No Smile is detected yet")
            }

        }
        ))
    cameraController.bindToLifecycle(lifecycleOwner)
    cameraController.cameraSelector = cameraSelectorFront
    previewView.controller = cameraController

}
// Function to generate a Toast
private fun mToast(context: Context,toast:String){
    Toast.makeText(context,toast , Toast.LENGTH_SHORT).show()
}