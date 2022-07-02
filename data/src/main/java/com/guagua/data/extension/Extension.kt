package com.guagua.data.extension

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


fun <T> tryOrNull(block: () -> T): T? {
    return try {
        block()
    } catch (e: Exception) {
        null
    }
}

@Suppress("SimpleDateFormat")
fun String.parseUTCDate(): Long? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return tryOrNull { dateFormat.parse(this).time }
}