package com.mohaberabi.crimeapp.core.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

fun Context.canResolveIntent(intent: Intent): Boolean {
    val resolved = packageManager.resolveActivity(
        intent,
        PackageManager.MATCH_DEFAULT_ONLY,
    )
    return resolved != null
}
