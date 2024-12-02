package com.example.medicare

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.DoctorProfile
import com.example.medicare.R

class DoctorAdapter(private val context: Context, private val doctors: List<Doctor>) :
    RecyclerView.Adapter<DoctorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.doctors_item, parent, false)
        return DoctorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return doctors.size
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.bind(doctor)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DoctorProfile::class.java)
            intent.putExtra("doctor", doctor)
            context.startActivity(intent)
        }


    }
}
