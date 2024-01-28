package com.valify.registeration.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.valify.registeration.navigation.NavigationItem
import com.valify.registeration.presentation.ui.register.RegisterScreen
import com.valify.registeration.presentation.ui.camera.CameraScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
         //   RegisterScreen(modifier = Modifier)
            AppNavHost(navController = rememberNavController())

        }
    }
}
@Composable
fun AppNavHost (
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Register.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Register.route) {
            RegisterScreen(navController=navController)
        }
        composable(NavigationItem.Camera.route) {
            CameraScreen(navController)
        }
    }
}



