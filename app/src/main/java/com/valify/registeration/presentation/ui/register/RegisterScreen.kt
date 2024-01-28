package com.valify.registeration.presentation.ui.register

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()) {
    val onLoading = viewModel.onLoading

    val (userName, setUserName) = viewModel.userName.collectAsMutableState()
    val (email, setEmail) = viewModel.email.collectAsMutableState()
    val (phone, setPhone) = viewModel.phone.collectAsMutableState()
    val (password, setPassword) = viewModel.password.collectAsMutableState()

    Scaffold(topBar = {
        Surface(shadowElevation = 5.dp) {
            CenterAlignedTopAppBar(title = { Text("Registration") })
        }
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            RegisterField(
                title = stringResource(id = R.string.user_name),
                hint = stringResource(id = R.string.user_name_hint),
                text = userName,
                onTextChanged = setUserName
            )
            RegisterField(
                title = stringResource(id = R.string.email),
                hint = stringResource(id = R.string.email_hint),
                text = email,
                onTextChanged = setEmail
            )
            RegisterField(
                title = stringResource(id = R.string.phone),
                hint = stringResource(id = R.string.phone_hint),
                text = phone,
                onTextChanged = setPhone
            )
            RegisterField(
                title = stringResource(id = R.string.password),
                hint = stringResource(id = R.string.password_hint),
                text = password,
                onTextChanged = setPassword
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally).padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue, contentColor = Color.White),
                onClick = { navController.navigate(NavigationItem.Camera.route) }) {
                Text(stringResource(R.string.submit))
            }
        }
    }
}

@Composable
fun RegisterField(
    title: String,
    hint: String,
    text: String,
    onTextChanged: (String) -> Unit,
    labelStyle: androidx.compose.ui.text.TextStyle = LocalTextStyle.current,
    labelColor: Color = Color.Black,
    labelTextAlign: TextAlign? = TextAlign.Start,
    textBorderColor: Color = Color.Gray,
    textBGColor: Color = Color.White,
    editTextColor: Color = Color.Black,
    editTextStyle: androidx.compose.ui.text.TextStyle = LocalTextStyle.current,
    hintColor: Color = Color.LightGray
) {
    Text(
        modifier = Modifier.padding(top = 4.dp, start = 4.dp),
        text = title,
        style = labelStyle,
        color = labelColor,
        textAlign = labelTextAlign
    )
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = textBorderColor,
                shape = RoundedCornerShape(16.dp),
            )
            .background(color = textBGColor),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions.Default,
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedTextColor = editTextColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        textStyle = editTextStyle,
        value = text,
        onValueChange = onTextChanged,
        shape = RoundedCornerShape(16.dp),
        placeholder = {
            Text(
                text = hint, color = hintColor
            )
        },
    )
}

@Composable
fun <T> MutableStateFlow<T>.collectAsMutableState(
    context: CoroutineContext = EmptyCoroutineContext
): MutableState<T> = MutableStateAdapter(state = collectAsState(context), mutate = { value = it })