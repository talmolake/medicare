package com.example.medicare

import android.health.connect.datatypes.ExerciseRoute.Location
import android.os.Parcel
import android.os.Parcelable

data class Doctor(
    val doctor: String,
    val category: String,
    val price: String,
    val rating: Int,
    val timestamp: String,
    val location: String,
    val days: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(doctor)
        parcel.writeString(category)
        parcel.writeString(price)
        parcel.writeInt(rating)
        parcel.writeString(timestamp)
        parcel.writeString(location)
        parcel.writeString(days)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Doctor> {
        override fun createFromParcel(parcel: Parcel): Doctor {
            return Doctor(parcel)
        }

        override fun newArray(size: Int): Array<Doctor?> {
            return arrayOfNulls(size)
        }
    }
}