package com.example.medicare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.databinding.AppointmentsPageBinding

class Appointments_list : BaseActivity() {

    private lateinit var binding: AppointmentsPageBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var upcomingAdapter: AppointmentAdapter
    private lateinit var previousAdapter: AppointmentAdapter

    private var upcomingAppointments = mutableListOf<AppointmentsDataClass>()
    private var previousAppointments = mutableListOf<AppointmentsDataClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AppointmentsPageBinding.inflate(layoutInflater)
        val contentFrame = findViewById<FrameLayout>(R.id.content_frame)
        contentFrame.addView(binding.root)

        databaseHelper = DatabaseHelper(this)

        val doctor = intent.getParcelableExtra<Doctor>("doctor")

        doctor?.let {
            val doctorNameTextView = findViewById<TextView>(R.id.doctor_name)
            val locationTextView = findViewById<TextView>(R.id.location_name)

            doctorNameTextView.text = it.doctor
            locationTextView.text = it.location
        }

        val upcomingRecyclerView: RecyclerView =
            findViewById(R.id.upcomingAppointments_recyclerView)
        val previousRecyclerView: RecyclerView =
            findViewById(R.id.previousAppointments_recyclerView)

        val reservations = mutableListOf<DatabaseContract.ReservationEntry>()
        val reservationsCursor = databaseHelper.readReservations()
        if (reservationsCursor.moveToFirst()) {
            do {
                val date = reservationsCursor.getString(
                    reservationsCursor.getColumnIndexOrThrow(DatabaseHelper.COL_DATE)
                )
                val time = reservationsCursor.getString(
                    reservationsCursor.getColumnIndexOrThrow(DatabaseHelper.COL_TIME)
                )
                val doctorName = reservationsCursor.getString(
                    reservationsCursor.getColumnIndexOrThrow(DatabaseHelper.COL_DOCTOR_NAME)
                )
                val location = reservationsCursor.getString(
                    reservationsCursor.getColumnIndexOrThrow(DatabaseHelper.COL_LOCATION)
                )
                val reservation = DatabaseContract.ReservationEntry(
                    date = date,
                    time = time,
                    doctor_name = doctorName,
                    location_name = location
                )
                reservations.add(reservation)
            } while (reservationsCursor.moveToNext())
            reservationsCursor.close()
        }

        upcomingAppointments = reservations.map {
            AppointmentsDataClass(
                date = it.date,
                time = it.time,
                doctor_name = doctor?.doctor ?: it.doctor_name,
                location_name = doctor?.location ?: it.location_name,
                status = "Upcoming"
            )
        }.toMutableList()

        previousAppointments = mutableListOf(
            AppointmentsDataClass(
                "2024-05-10",
                "Benjamin Dube",
                "11:00 AM",
                "Sidilega",
                "Completed"
            ),
            AppointmentsDataClass(
                "2024-05-12",
                "Christopher Bhang",
                "02:00 PM",
                "Mogoditshane",
                "Completed"
            )
        )

        upcomingRecyclerView.layoutManager = LinearLayoutManager(this)
        upcomingAdapter = AppointmentAdapter(this, upcomingAppointments, true, previousAppointments) // Pass the list of previous appointments
        upcomingRecyclerView.adapter = upcomingAdapter

        previousRecyclerView.layoutManager = LinearLayoutManager(this)
        previousAdapter = AppointmentAdapter(this, previousAppointments, false, previousAppointments, upcomingAdapter)
        previousRecyclerView.adapter = previousAdapter

        // Pass the previousAdapter to the upcomingAdapter so that it can update the previous list
        upcomingAdapter.previousAdapter = previousAdapter

        val searchBar: EditText = findViewById(R.id.search_bar)
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterAppointments(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterAppointments(query: String) {
        if (query.isEmpty()) {
            // If the search query is empty, reset the lists to the original appointments
            upcomingAdapter.updateList(upcomingAppointments)
            previousAdapter.updateList(previousAppointments)
        } else {
            val filteredUpcoming = upcomingAppointments.filter {
                it.date.contains(query, ignoreCase = true) ||
                        it.time.contains(query, ignoreCase = true) ||
                        it.doctor_name.contains(query, ignoreCase = true) ||
                        it.location_name.contains(query, ignoreCase = true)
            }
            upcomingAdapter.updateList(filteredUpcoming)

            val filteredPrevious = previousAppointments.filter {
                it.date.contains(query, ignoreCase = true) ||
                        it.time.contains(query, ignoreCase = true) ||
                        it.doctor_name.contains(query, ignoreCase = true) ||
                        it.location_name.contains(query, ignoreCase = true)
            }
            previousAdapter.updateList(filteredPrevious)
        }
    }
}

