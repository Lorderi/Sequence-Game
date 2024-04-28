package ru.lorderi.sequencegame.ui.util

fun getFormattedLabel(state: Boolean): String {
    val value = if (state) {
        "ON"
    } else {
        "OFF"
    }
    return value
}