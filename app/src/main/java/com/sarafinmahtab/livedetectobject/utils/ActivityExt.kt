package com.sarafinmahtab.livedetectobject.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS


fun Activity.openAppSetting() {
    val uri = Uri.fromParts("package", packageName, null)

    val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = uri
    }

    startActivity(intent)
}
