package com.sarafinmahtab.livedetectobject.ui.components

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.sarafinmahtab.livedetectobject.R
import com.sarafinmahtab.livedetectobject.ui.theme.LiveObjectDetectTheme


@Composable
fun AppAlertDialog(
    dialogTitle: String = "",
    showTitle: Boolean = false,
    dialogText: String,
    onConfirmation: () -> Unit,
    @StringRes confirmTextRes: Int = R.string.confirm,
    onDismissRequest: () -> Unit = {},
    @StringRes dismissTextRes: Int = R.string.dismiss,
    isCancellable: Boolean = false,
    icon: ImageVector? = null,
    @StringRes iconContentDescriptionRes: Int = R.string.alert_icon,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        icon = {
            icon?.let {
                Icon(it, contentDescription = stringResource(id = iconContentDescriptionRes))
            }
        },
        title = {
            if (showTitle) {
                Text(text = dialogTitle)
            }
        },
        text = {
            Text(text = dialogText)
        },
        confirmButton = {
            TextButton(
                onClick = onConfirmation,
            ) {
                Text(stringResource(id = confirmTextRes))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
            ) {
                Text(stringResource(id = dismissTextRes))
            }
        },
        properties = DialogProperties(
            dismissOnClickOutside = isCancellable,
            dismissOnBackPress = isCancellable,
        ),
        containerColor = MaterialTheme.colorScheme.surface,
        textContentColor = MaterialTheme.colorScheme.onSurface,
    )
}


@Preview
@Composable
fun AppAlertDialogPreview() {
    LiveObjectDetectTheme {
        AppAlertDialog(
            dialogText = "Are you sure you want to logout?",
            showTitle = false,
            onConfirmation = {},
        )
    }
}
