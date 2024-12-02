package com.example.medicare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicare.databinding.DoctorProfileBinding

class DoctorProfile : BaseActivity() {
    private lateinit var binding: DoctorProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DoctorProfileBinding.inflate(layoutInflater)
        val contentFrame = findViewById<FrameLayout>(R.id.content_frame)
        contentFrame.addView(binding.root)

        val doctor = intent.getParcelableExtra<Doctor>("doctor")

        if (doctor != null) {
        val doctorNameTextView = findViewById<TextView>(R.id.doctor_name)
        val categoryTextView = findViewById<TextView>(R.id.category)
        val priceTextView = findViewById<TextView>(R.id.price)
        val availableTimesTextView = findViewById<TextView>(R.id.available_time)
        val daysTextView = findViewById<TextView>(R.id.date)
        val locationTextView = findViewById<TextView>(R.id.location_name)
        val bookReservationButton = findViewById<Button>(R.id.bookReservationButton)

        doctorNameTextView.text = doctor?.doctor
        categoryTextView.text = doctor?.category
        priceTextView.text = doctor?.price
        availableTimesTextView.text = doctor?.timestamp
        daysTextView.text = doctor?.days
        locationTextView.text = doctor?.location

        bookReservationButton.setOnClickListener {
            val intent = Intent(this, BookReservationActivity::class.java)
            intent.putExtra("doctor", doctor?.doctor)
            intent.putExtra("location", doctor?.location)
            startActivity(intent)
            finish()
        }
        } else {
                // Handle case where doctor is null, such as displaying an error message or finishing the activity
                Toast.makeText(this, "Doctor information not found", Toast.LENGTH_SHORT).show()
                finish()

        }
    }
}
