package com.example.medicare

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import android.widget.FrameLayout
import com.example.medicare.databinding.DoctorsPageBinding

class DoctorsPage : BaseActivity() {

    private lateinit var binding: DoctorsPageBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var doctorAdapter: DoctorAdapter
    private lateinit var doctorList: List<Doctor>
    private lateinit var filteredDoctorList: MutableList<Doctor>
    private lateinit var searchBar: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DoctorsPageBinding.inflate(layoutInflater)
        val contentFrame = findViewById<FrameLayout>(R.id.content_frame)
        contentFrame.addView(binding.root)


        doctorList = listOf(
            Doctor("Benjamin Dube", "Dental", "P150", 4, "08:00-15:00", "Sidilega", "Monday-Friday"),
            Doctor("Edwin Raw", "Physiology", "P150", 5, "08:00-15:00", "Marina", "Monday-Friday"),
            Doctor("Teto Tito", "Cardiology", "P150", 3, "08:00-15:00", "Bokamoso", "Monday-Friday"),
            Doctor("Brian Shlo", "Medicine", "P150", 4, "08:00-15:00", "GPH", "Monday-Friday"),
            Doctor("Thapong Chris", "Dental", "P150", 4, "08:00-15:00", "Sidilega", "Monday-Friday"),
            Doctor("Nametso Dube", "Physiology", "P150", 4, "08:00-15:00", "GPH", "Monday-Friday"),
            Doctor("Christopher Bhang", "Medicine", "P150", 4, "08:00-15:00", "Mogoditshane", "Monday-Friday"),
            Doctor("Neo Molake", "Optimery", "P150", 4, "08:00-15:00", "Marina", "Monday-Friday")
        )

        filteredDoctorList = doctorList.toMutableList()

        recyclerView = findViewById(R.id.doctors_recyclerView)
        doctorAdapter = DoctorAdapter(this, filteredDoctorList)
        recyclerView.adapter = doctorAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchBar = findViewById(R.id.search_bar)
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterDoctors(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterDoctors(query: String) {
        filteredDoctorList.clear()
        if (query.isEmpty()) {
            filteredDoctorList.addAll(doctorList)
        } else {
            val filteredList = doctorList.filter {
                it.doctor.contains(query, true) || it.category.contains(query, true)
            }
            filteredDoctorList.addAll(filteredList)
        }
        doctorAdapter.notifyDataSetChanged()
    }
}
