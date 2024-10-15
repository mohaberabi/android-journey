package com.mohaberabi.crimeapp.core.utils

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract

fun Context.queryContact(uri: Uri): String {
    val projection = arrayOf(
        ContactsContract.Contacts.DISPLAY_NAME
    )
    val resolver = contentResolver
    val curosor = resolver.query(
        uri,
        projection, null, null, null
    )
    return curosor?.use { contactCursor ->
        if (contactCursor.moveToFirst()) {
            val contact = contactCursor.getString(0)
            contact
        } else {
            ""
        }
    } ?: ""
}