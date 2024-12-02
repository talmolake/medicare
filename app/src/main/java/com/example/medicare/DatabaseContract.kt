package com.example.medicare

import android.health.connect.datatypes.ExerciseRoute.Location

data class DatabaseContract(
    val id: Long = -1,
    val name: String,
    val email: String,
    val password: String
) {
    data class ReservationEntry(
        val id: Long = -1,
        val date: String,
        val time: String,
        val doctor_name: String,
        val location_name: String
    )
}
