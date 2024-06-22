package com.sarafinmahtab.livedetectobject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarafinmahtab.livedetectobject.ui.camera.CameraScreen
import com.sarafinmahtab.livedetectobject.ui.start.StartScreen
import com.sarafinmahtab.livedetectobject.ui.theme.LiveObjectDetectTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            LiveObjectDetectTheme {
                Navigation(navController = navController)
            }
        }
    }
}
