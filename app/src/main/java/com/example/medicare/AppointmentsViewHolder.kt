//package com.example.medicare
//
//import android.content.Context
//import android.content.DialogInterface
//import android.content.Intent
//import android.view.View
//import android.widget.ImageButton
//import android.widget.TextView
//import android.widget.Toast
//import androidx.core.content.ContextCompat.startActivity
//import androidx.recyclerview.widget.RecyclerView
//import androidx.appcompat.app.AlertDialog
//
//class AppointmentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    private val dateTextView: TextView = itemView.findViewById(R.id.set_date)
//    private val doctorTextView: TextView = itemView.findViewById(R.id.doctor_name)
//    private val timeTextView: TextView = itemView.findViewById(R.id.set_time)
//    private val locationTextView: TextView = itemView.findViewById(R.id.location_name)
//    private val statusTextView: TextView = itemView.findViewById(R.id.status)
//    private val cancelButton: ImageButton = itemView.findViewById(R.id.cancelButton)
//    private val context: Context = itemView.context
//
//    fun bind(appointment: AppointmentsDataClass) {
//        dateTextView.text = appointment.date
//        doctorTextView.text = appointment.doctor_name
//        timeTextView.text = appointment.time
//        locationTextView.text = appointment.location_name
//        statusTextView.text = appointment.status
//
//
//        cancelButton.setOnClickListener {
//            showCancelConfirmationDialog(appointment)
//        }
//    }
//
//    private fun showCancelConfirmationDialog(appointment: AppointmentsDataClass) {
//        AlertDialog.Builder(context)
//            .setTitle("Cancel Appointment")
//            .setMessage("Are you sure you want to cancel this appointment?")
//            .setPositiveButton("Yes") { _, _ ->
//                // Handle cancellation confirmation
//                handleCancelConfirmation(appointment)
//            }
//            .setNegativeButton("No", null)
//            .show()
//    }
//
//    private fun handleCancelConfirmation(appointment: AppointmentsDataClass) {
//        val db = DatabaseHelper(context)
//        if (db.deleteReservation(appointment.date, appointment.time)) {
//            // Notify the activity or fragment to remove the item from the list
//            Toast.makeText(context, "Appointment canceled successfully", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(context, "Failed to cancel appointment", Toast.LENGTH_SHORT).show()
//        }
//    }
//}
