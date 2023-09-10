package com.whyraya.moviedb.utils

import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val DATE_FORMAT_V01 = "yyyy-MM-dd"
const val DATE_FORMAT_YEAR = "yyyy"

fun String.formatDate(format: String = DATE_FORMAT_V01): Date? {
    return try {
        if (this.isBlank()) {
            null
        } else SimpleDateFormat(format, Locale.getDefault()).parse(this)
    } catch (e: ParseException) {
        Timber.e(e.localizedMessage.orEmpty())
        null
    }
}

fun Date?.formatDate(format: String = DATE_FORMAT_V01): String = this?.let {
    SimpleDateFormat(format, Locale.getDefault()).format(it)
}.orEmpty()

fun String?.formatDate(
    prevFormat: String = DATE_FORMAT_V01,
    nextFormat: String
): String = this?.formatDate(prevFormat)?.formatDate(nextFormat).orEmpty()

fun String.formatYear(): String = formatDate(nextFormat = DATE_FORMAT_YEAR)

fun String.formatUtc(): String = formatDate(
    prevFormat = DATE_FORMAT_UTC,
    nextFormat = DATE_FORMAT_V01
)
