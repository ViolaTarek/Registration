package com.valify.registeration.presentation.ui.camera.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valify.registeration.R

@Composable
fun NoPermissionScreen(onRequestPermission: () -> Unit) {
    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
        Text(text = stringResource(R.string.no_permission_msg), style = TextStyle())
        Button(onClick = onRequestPermission,Modifier.padding(top=16.dp)) {
            Icon(painter = painterResource(id = R.drawable.icon_camera), modifier = Modifier.size(22.dp).padding(end = 8.dp) , contentDescription = "Camera")
            Text(text = stringResource(R.string.grant_permission))
        }

    }
}
@Preview
@Composable
private fun Preview_NoPermissionScreen(){
    NoPermissionScreen (onRequestPermission = {})
}