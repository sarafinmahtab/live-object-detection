package com.sarafinmahtab.livedetectobject.ui.start

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.sarafinmahtab.livedetectobject.NavigationScreen
import com.sarafinmahtab.livedetectobject.R
import com.sarafinmahtab.livedetectobject.ui.components.AppAlertDialog
import com.sarafinmahtab.livedetectobject.ui.components.AppBar
import com.sarafinmahtab.livedetectobject.ui.start.PermissionRationaleState.NO
import com.sarafinmahtab.livedetectobject.ui.start.PermissionRationaleState.SHOW_SETTINGS_ON_NEXT
import com.sarafinmahtab.livedetectobject.ui.start.PermissionRationaleState.YES
import com.sarafinmahtab.livedetectobject.ui.theme.LiveObjectDetectTheme
import com.sarafinmahtab.livedetectobject.utils.openAppSetting

enum class PermissionRationaleState { NO, YES, SHOW_SETTINGS_ON_NEXT }

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun StartScreen(
    navController: NavController,
) {
    val scrollState = rememberScrollState()
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    val permissionRequestLaunched = rememberSaveable { mutableStateOf(false) }
    val showCameraPermissionRationale = rememberSaveable { mutableStateOf(NO) }
    val showPermissionRequestDeniedAlert = rememberSaveable { mutableStateOf(false) }

    StartContent(
        scrollState = scrollState,
        onClickOpenCamera = {
            if (cameraPermissionState.status.isGranted) {
                navController.navigate(NavigationScreen.Camera.route)
            } else {
                cameraPermissionState.launchPermissionRequest()
                permissionRequestLaunched.value = true

                if (showCameraPermissionRationale.value == SHOW_SETTINGS_ON_NEXT) {
                    showPermissionRequestDeniedAlert.value = true
                }
            }
        },
    )

    if (cameraPermissionState.status.isGranted && permissionRequestLaunched.value) {
        LaunchedEffect(key1 = Unit) {
            permissionRequestLaunched.value = false
            navController.navigate(NavigationScreen.Camera.route)
        }
    } else if (cameraPermissionState.status.shouldShowRationale) {
        LaunchedEffect(key1 = Unit) {
            showCameraPermissionRationale.value = YES
        }
    }

    if (showCameraPermissionRationale.value == YES) {
        AppAlertDialog(
            onDismissRequest = { showCameraPermissionRationale.value = NO },
            onConfirmation = {
                showCameraPermissionRationale.value = SHOW_SETTINGS_ON_NEXT
                cameraPermissionState.launchPermissionRequest()
            },
            dialogTitle = stringResource(id = R.string.camera_permission_required_title),
            dialogText = stringResource(id = R.string.camera_permission_required_details),
        )
    }

    if (showPermissionRequestDeniedAlert.value) {
        val activity = LocalContext.current as Activity

        AppAlertDialog(
            onDismissRequest = { showPermissionRequestDeniedAlert.value = false },
            onConfirmation = {
                showPermissionRequestDeniedAlert.value = false
                activity.openAppSetting()
            },
            dialogTitle = stringResource(id = R.string.camera_permission_required_title),
            dialogText = stringResource(id = R.string.camera_permission_denied_details),
            confirmTextRes = R.string.app_settings,
        )
    }
}

@Composable
fun StartContent(
    scrollState: ScrollState,
    onClickOpenCamera: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppBar() },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Card(
                modifier = Modifier.padding(10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    painter = painterResource(id = R.drawable.bg_image),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceVariant),
                    contentDescription = "Image Background",
                )
            }
            ElevatedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClickOpenCamera,
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = "Camera",
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.open_camera),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Preview
@Composable
fun StartContentPreview() {
    LiveObjectDetectTheme {
        StartContent(
            scrollState = rememberScrollState(),
            onClickOpenCamera = {},
        )
    }
}
