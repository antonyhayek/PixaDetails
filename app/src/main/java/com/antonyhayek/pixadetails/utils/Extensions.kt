package com.antonyhayek.pixadetails.utils

import android.text.TextUtils
import android.util.Patterns
import java.text.CharacterIterator
import java.text.StringCharacterIterator

fun String.isValidEmail() =
    !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun humanReadableByteCountSI(bytes: Long): String? {
    var bytes = bytes
    if (-1024 < bytes && bytes < 1024) {
        return "$bytes B"
    }
    val ci: CharacterIterator = StringCharacterIterator("KMGTPE")
    while (bytes <= -999950 || bytes >= 999950) {
        bytes /= 1024
        ci.next()
    }
    return java.lang.String.format("%.1f %cB", bytes / 1024.0, ci.current())
}