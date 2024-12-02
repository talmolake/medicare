package com.example.medicare

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.FrameLayout
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import com.example.medicare.R
import com.example.medicare.databinding.BookReservationBinding

class BookReservationActivity : BaseActivity() {

    private lateinit var binding: BookReservationBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var editTextDate: EditText
    private lateinit var editTextTime: EditText
    private lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =BookReservationBinding.inflate(layoutInflater)
        val contentFrame = findViewById<FrameLayout>(R.id.content_frame)
        contentFrame.addView(binding.root)

        databaseHelper = DatabaseHelper(this)
        editTextDate = findViewById(R.id.editTextDate)
        editTextTime = findViewById(R.id.editTextTime)
        confirmButton = findViewById(R.id.confirm)

        editTextDate.setOnClickListener {
            showDatePickerDialog()
        }

        editTextTime.setOnClickListener {
            showTimePickerDialog()
        }
        confirmButton.setOnClickListener {
            val date = editTextDate.text.toString().trim()
            val time = editTextTime.text.toString().trim()
            val doctorName = intent.getStringExtra("doctor")
            val location = intent.getStringExtra("location")

            if (date.isNotEmpty() && time.isNotEmpty() && doctorName != null && location != null) {
                // Insert reservation into database
                databaseHelper.insertReservation(date, time, doctorName, location)
                Toast.makeText(this, "Reservation Confirmed", Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearFields() {
        editTextDate.text.clear()
        editTextTime.text.clear()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                editTextDate.setText(dateFormat.format(selectedDate.time))
            },
            year, month, dayOfMonth
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _: TimePicker?, hourOfDay: Int, minute: Int ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                editTextTime.setText(timeFormat.format(selectedTime.time))
            },
            hour, minute, true
        )
        timePickerDialog.show()
    }
}


//    private fun saveReservation() {
//        val date = editTextDate.text.toString().trim()
//        val time = editTextTime.text.toString().trim()
//

//
//        val values = ContentValues().apply {
//            put(DatabaseHelper.COL_DATE, date)
//            put(DatabaseHelper.COL_TIME, time)
//            // Add other columns as needed
//        }
//
//        val newRowId = db.insert(DatabaseHelper.TABLE_NAME_RESERVATION, null, values)
//
//        if (newRowId != -1L) {
//            // Insert successful
//            showToast("Reservation booked successfully")
//        } else {
//            // Insert failed
//            showToast("Failed to book reservation")
//        }
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
//}
