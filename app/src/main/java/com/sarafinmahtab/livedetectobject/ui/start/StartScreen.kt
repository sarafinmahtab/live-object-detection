package com.sarafinmahtab.livedetectobject.ui.start

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.sarafinmahtab.livedetectobject.R
import com.sarafinmahtab.livedetectobject.ui.components.AppAlertDialog
import com.sarafinmahtab.livedetectobject.ui.components.AppBar
import com.sarafinmahtab.livedetectobject.ui.theme.LiveObjectDetectTheme


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun StartScreen(
    navController: NavController,
) {
    val scrollState = rememberScrollState()
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    val showCameraPermissionRationale = rememberSaveable { mutableStateOf(false) }

    StartContent(
        scrollState = scrollState,
        onClickOpenCamera = {
            if (cameraPermissionState.status.isGranted) {
                navController.navigate("camera")
            } else {
                if (cameraPermissionState.status.shouldShowRationale) {
                    showCameraPermissionRationale.value = true
                }

                //TODO handle error message when permission denied and showRationale is false
                cameraPermissionState.launchPermissionRequest()
            }
        },
    )

    if (showCameraPermissionRationale.value) {
        AppAlertDialog(
            onDismissRequest = { showCameraPermissionRationale.value = false },
            onConfirmation = {
                showCameraPermissionRationale.value = false
                cameraPermissionState.launchPermissionRequest()
            },
            dialogTitle = stringResource(id = R.string.camera_permission_required_title),
            dialogText = stringResource(id = R.string.camera_permission_required_details),
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
