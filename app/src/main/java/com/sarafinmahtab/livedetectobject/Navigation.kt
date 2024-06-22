package com.sarafinmahtab.livedetectobject

import androidx.annotation.StringDef
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sarafinmahtab.livedetectobject.Routes.Companion.CAMERA
import com.sarafinmahtab.livedetectobject.Routes.Companion.START
import com.sarafinmahtab.livedetectobject.ui.camera.CameraScreen
import com.sarafinmahtab.livedetectobject.ui.start.StartScreen


@Retention(AnnotationRetention.SOURCE)
@StringDef(
    START,
    CAMERA,
)
annotation class Routes {
    companion object {
        const val START = "start"
        const val CAMERA = "camera"
    }
}


sealed class NavigationScreen(
    val route: String,
    @StringRes val resourceId: Int
) {
    data object Start : NavigationScreen(START, R.string.start)
    data object Camera : NavigationScreen(CAMERA, R.string.camera)
}


@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.Start.route,
    ) {
        composable(NavigationScreen.Start.route) {
            StartScreen(navController)
        }
        composable(NavigationScreen.Camera.route) {
            CameraScreen(navController)
        }
    }
}
