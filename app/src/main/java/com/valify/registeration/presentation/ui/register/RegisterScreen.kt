package com.valify.registeration.presentation.ui.register

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.valify.registeration.R
import com.valify.registeration.navigation.NavigationItem
import com.valify.registeration.presentation.ui.camera.CameraScreen
import com.valify.registeration.utils.MutableStateAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Error
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()) {
    if (viewModel.registerDone) {
        LaunchedEffect(Unit) {
            navController.navigate(NavigationItem.Camera.route)
            //navController.popBackStack()
        }
    }
    Scaffold(topBar = {
        Surface(shadowElevation = 5.dp) {
            CenterAlignedTopAppBar(title = { Text("Registration") })
        }
    }) {
        if (viewModel.error.isNotEmpty()) {
            mToast(LocalContext.current, viewModel.error)
            viewModel.resetError()
        }
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            RegisterFields(viewModel = viewModel)

            Button(modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                onClick = { viewModel.validateData() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_save),
                    modifier = Modifier
                        .size(22.dp)
                        .padding(end = 8.dp),
                    contentDescription = "Camera"
                )
                Text(text = stringResource(R.string.submit))
            }
        }
    }
}


@Composable
fun RegisterFields(
    viewModel: RegisterViewModel
) {
    val name by viewModel.userName.collectAsState()
    val email by viewModel.email.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val pass by viewModel.password.collectAsState()

    var showPassword by remember { mutableStateOf(value = false) }

    OutlinedTextField(
        value = name ?: "",
        label = { Text(stringResource(id = R.string.user_name)) },
        onValueChange = { viewModel.setName(it) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        textStyle = MaterialTheme.typography.labelMedium,
        singleLine = true,
        placeholder = { Text(text = stringResource(id = R.string.user_name_hint)) },
        shape = RoundedCornerShape(16.dp),

        )
    OutlinedTextField(
        value = email,
        label = { Text(stringResource(id = R.string.email)) },
        onValueChange = { viewModel.setEmail(it) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = true,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        textStyle = MaterialTheme.typography.labelMedium,
        singleLine = true,
        placeholder = { Text(text = stringResource(id = R.string.email_hint)) },
        shape = RoundedCornerShape(16.dp),
    )
    OutlinedTextField(
        value = phone,
        label = { Text(stringResource(id = R.string.phone)) },
        onValueChange = { viewModel.setPhone(it) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = true,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        textStyle = MaterialTheme.typography.labelMedium,
        singleLine = true,
        placeholder = { Text(text = stringResource(id = R.string.phone_hint)) },
        shape = RoundedCornerShape(16.dp),
    )
    OutlinedTextField(
        value = pass,
        label = { Text(stringResource(id = R.string.password)) },
        onValueChange = { viewModel.setPassword(it) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        textStyle = MaterialTheme.typography.labelMedium,
        singleLine = true,
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        placeholder = { Text(text = stringResource(id = R.string.password_hint)) },
        shape = RoundedCornerShape(16.dp),
        trailingIcon = {
            if (showPassword) {
                IconButton(onClick = { showPassword = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "hide_password"
                    )
                }
            } else {
                IconButton(
                    onClick = { showPassword = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "hide_password"
                    )
                }
            }
        }
    )

}

@Composable
fun <T> MutableStateFlow<T>.collectAsMutableState(
    context: CoroutineContext = EmptyCoroutineContext
): MutableState<T> =
    MutableStateAdapter(state = collectAsState(context), mutate = { value = it })

private fun mToast(context: Context, toast: String) {
    Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
}