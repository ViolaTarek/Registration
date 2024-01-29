package com.valify.smileregisteration.presentation.ui.camera

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.isGranted
import com.valify.smileregisteration.data.AppDatabase
import com.valify.smileregisteration.data.UserDao
import com.valify.smileregisteration.data.UserRepository
import com.valify.smileregisteration.presentation.ui.camera.composable.CameraScreenContent
import com.valify.smileregisteration.presentation.ui.camera.composable.NoPermissionScreen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(navController: NavController, viewModel: CameraViewModel = hiltViewModel()) {
    val activity = (LocalContext.current as? Activity)
    if (viewModel.captureDone) {
        mToast(context = LocalContext.current, viewModel.data)
        viewModel.captureDone = false
          activity?.finish()
    }
    BackHandler {
        navController.navigateUp()
    }
    val cameraPermissionState: PermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)

    MainContent(
        viewModel,
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest
    )
}

@Composable
private fun MainContent(
    viewModel: CameraViewModel,
    hasPermission: Boolean,
    onRequestPermission: () -> Unit
) {

    if (hasPermission) {
        CameraScreenContent(viewModel)
    } else {
        NoPermissionScreen(onRequestPermission)
    }

}

fun mToast(context: Context, toast: String) {
    Toast.makeText(context, toast, Toast.LENGTH_LONG).show()
}



