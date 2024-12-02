package com.example.medicare

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val doctorName: TextView = itemView.findViewById(R.id.doctor_name)
    private val category: TextView = itemView.findViewById(R.id.category)
    private val rating: TextView = itemView.findViewById(R.id.rating)
    private val availableTimes: TextView = itemView.findViewById(R.id.available_times)

    fun bind(doctor: Doctor) {
        doctorName.text = doctor.doctor
        category.text = doctor.category
        rating.text = doctor.rating.toString()
        availableTimes.text = doctor.timestamp
    }
}