package com.backdrops.app.util

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.toRelativeDateFormat(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    val date = try {
        sdf.parse(this)
    } catch (e:Exception) {
        Date()
    }
    return DateUtils.getRelativeTimeSpanString(date.time).toString()
}

fun String.toDate(): Date {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    val date = try {
        sdf.parse(this)
    } catch (e:Exception) {
        Date()
    }
    return date
}
