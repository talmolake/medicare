package com.example.medicare

import android.os.Parcel
import android.os.Parcelable

data class AppointmentsDataClass (
    val date: String,
    val doctor_name: String,
    val time: String,
    val location_name: String,
    var status: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(doctor_name)
        parcel.writeString(time)
        parcel.writeString(location_name)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AppointmentsDataClass> {
        override fun createFromParcel(parcel: Parcel): AppointmentsDataClass {
            return AppointmentsDataClass(parcel)
        }

        override fun newArray(size: Int): Array<AppointmentsDataClass?> {
            return arrayOfNulls(size)
        }
    }
}