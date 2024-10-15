package com.mohaberabi.crimeapp.core.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File


fun Context.createUriFroFile(name: String, ext: String): Pair<Uri, File> {
    val fileName = "${name}.${ext}"
    val file = File(filesDir, fileName)
    val uri = FileProvider.getUriForFile(this, AppConst.FILE_AUTHORITY, file)
    return uri to file
}