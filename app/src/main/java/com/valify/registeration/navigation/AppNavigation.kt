package com.valify.registeration.navigation

enum class Screen {
    REGISTER,
    CAMERA,
}
sealed class NavigationItem(val route: String) {
    data object Register : NavigationItem(Screen.REGISTER.name)
    data object Camera : NavigationItem(Screen.CAMERA.name)
}