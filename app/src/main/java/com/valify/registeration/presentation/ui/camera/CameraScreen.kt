package com.valify.registeration.presentation.ui.camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.isGranted
import com.valify.registeration.presentation.ui.camera.composable.CameraScreenContent
import com.valify.registeration.presentation.ui.camera.composable.NoPermissionScreen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(navController: NavController) {
    val cameraPermissionState: PermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)

    MainContent(
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest
    )
}

@Composable
private fun MainContent(hasPermission: Boolean, onRequestPermission: () -> Unit) {
    if (hasPermission) {
        CameraScreenContent()
    } else {
        NoPermissionScreen(onRequestPermission)
    }
}

@Preview
@Composable
private fun Preview_MainContent() {
    MainContent(hasPermission = false, onRequestPermission = {})
}

