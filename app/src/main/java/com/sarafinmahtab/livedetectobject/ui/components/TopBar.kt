package com.sarafinmahtab.livedetectobject.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarafinmahtab.livedetectobject.R
import com.sarafinmahtab.livedetectobject.ui.theme.LiveObjectDetectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .padding(12.dp)
                    .size(36.dp),
                painter = painterResource(id = R.drawable.ic_app_logo),
                contentDescription = "App Logo",
                tint = MaterialTheme.colorScheme.primary,
            )
        },
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
            )
        },
    )
}

@Preview
@Composable
fun AppBarPreview() {
    LiveObjectDetectTheme {
        AppBar()
    }
}
