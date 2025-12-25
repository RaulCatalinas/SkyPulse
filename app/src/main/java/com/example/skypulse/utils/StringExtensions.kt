package com.example.skypulse.utils

fun String.capitalizeFirstLetter(): String {
    if (this.isBlank()) return this

    val firstChar = this.first().uppercaseChar()

    return firstChar + this.drop(1)
}
