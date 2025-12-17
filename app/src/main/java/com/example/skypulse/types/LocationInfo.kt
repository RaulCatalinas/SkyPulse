package com.example.skypulse.types

data class LocationInfo(
    val city: String,
    val country: String
) {
    companion object {
        val UNKNOWN = LocationInfo("Unknown", "Unknown")
    }
}