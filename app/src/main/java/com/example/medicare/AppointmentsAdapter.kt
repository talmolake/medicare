package com.example.medicare

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog

class AppointmentAdapter(
    private val context: Context,
    private val appointments: MutableList<AppointmentsDataClass>,
    private val isUpcoming: Boolean,
    private val previousAppointments: MutableList<AppointmentsDataClass>,
    var previousAdapter: AppointmentAdapter? = null
) : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    inner class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.set_date)
        val doctorTextView: TextView = itemView.findViewById(R.id.doctor_name)
        val timeTextView: TextView = itemView.findViewById(R.id.set_time)
        val locationTextView: TextView = itemView.findViewById(R.id.location_name)
        val statusTextView: TextView = itemView.findViewById(R.id.status)
        val summaryButton: Button? = itemView.findViewById(R.id.summary)
        val cancelButton: ImageButton? = itemView.findViewById(R.id.cancelButton)
        val checkButton: ImageButton? = itemView.findViewById(R.id.checkButton)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val appointment = appointments[position]
                    val intent = Intent(context, Appointments_list::class.java)
                    intent.putExtra("doctor", appointment.doctor_name)
                    context.startActivity(intent)
                }
            }

            checkButton?.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val appointment = appointments[position]
                    showCheckInConfirmationDialog(appointment)
                }
            }
            cancelButton?.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val appointment = appointments[position]
                    showCancelConfirmationDialog(appointment)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val layoutId = if (isUpcoming) R.layout.appointment_item else R.layout.previous_appointments
        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return AppointmentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val currentAppointment = appointments[position]
        holder.dateTextView.text = "Date: ${currentAppointment.date}"
        holder.doctorTextView.text = "Doctor: ${currentAppointment.doctor_name}"
        holder.timeTextView.text = "Time: ${currentAppointment.time}"
        holder.locationTextView.text = "Location: ${currentAppointment.location_name}"
        holder.statusTextView.text = "Status: ${currentAppointment.status}"

        if (!isUpcoming) {
            holder.summaryButton?.setOnClickListener {
                // Handle summary button click
            }
        }

    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    fun updateList(newAppointments: List<AppointmentsDataClass>) {
        appointments.clear()
        appointments.addAll(newAppointments)
        notifyDataSetChanged()
    }

    private fun showCheckInConfirmationDialog(appointment: AppointmentsDataClass) {
        AlertDialog.Builder(context)
            .setTitle("Check In Appointment")
            .setMessage("Are you checking in for this appointment?")
            .setPositiveButton("Yes") { _, _ ->
                handleCheckInConfirmation(appointment)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun handleCheckInConfirmation(appointment: AppointmentsDataClass) {
        appointment.status = "Completed"

        // Check if appointment already exists in previousAppointments
        if (!previousAppointments.contains(appointment)) {
            previousAppointments.add(appointment)
        }

        // Remove appointment from upcoming list
        appointments.remove(appointment)

        // Notify adapters of changes
        notifyDataSetChanged()

        Toast.makeText(context, "Checked in successfully", Toast.LENGTH_SHORT).show()
    }

    private fun showCancelConfirmationDialog(appointment: AppointmentsDataClass) {
        AlertDialog.Builder(context)
            .setTitle("Cancel Appointment")
            .setMessage("Are you sure you want to cancel this appointment?")
            .setPositiveButton("Yes") { _, _ ->
                handleCancelConfirmation(appointment)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun handleCancelConfirmation(appointment: AppointmentsDataClass) {
        val db = DatabaseHelper(context)
        if (db.deleteReservation(appointment.date, appointment.time)) {
            appointments.remove(appointment)
            notifyDataSetChanged()
            Toast.makeText(context, "Appointment canceled successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed to cancel appointment", Toast.LENGTH_SHORT).show()
        }
    }
}
