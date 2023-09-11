package com.whyraya.moviedb.utils

import kotlin.math.roundToInt

fun Int?.orZero(): Int = this ?: 0

fun Long?.orZero(): Long = this ?: 0L

fun Float?.orZero(): Float = this ?: 0F

fun Double?.orZero(): Double = this ?: 0.0

fun Boolean?.orFalse(): Boolean = this ?: false

fun Double.roundDecimal() = when {
    this <= 0 -> this
    else -> {
        (this * 10.0).roundToInt() / 10.0
    }
}
