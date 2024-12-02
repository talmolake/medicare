package com.example.medicare

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.medicare.databinding.PrescriptionsAndMedicineBinding

class PrescriptionsAndMedicines : BaseActivity() {
    private lateinit var dbHelper: PrescriptionDBHelper
    private lateinit var edtMedicineName: EditText
    private lateinit var edtDosage: EditText
    private lateinit var listView: ListView
    private lateinit var viewMedicationsButton: Button
    private lateinit var binding: PrescriptionsAndMedicineBinding

    private var selectedEntryId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PrescriptionsAndMedicineBinding.inflate(layoutInflater)
        val contentFrame = findViewById<FrameLayout>(R.id.content_frame)
        contentFrame.addView(binding.root)

        dbHelper = PrescriptionDBHelper(this)
        edtMedicineName = findViewById(R.id.medicineNameEditText)
        edtDosage = findViewById(R.id.dosageEditText)
        listView = findViewById(R.id.medicationListView)
        viewMedicationsButton = findViewById(R.id.viewMedicationsButton)

        findViewById<Button>(R.id.addButton).setOnClickListener {
            insertData()
        }
        findViewById<Button>(R.id.updateButton).setOnClickListener {
            updateData()
        }
        findViewById<Button>(R.id.deleteButton).setOnClickListener {
            deleteData()
        }
        viewMedicationsButton.setOnClickListener {
            readData()
        }
    }

    private fun insertData() {
        val medicineName = edtMedicineName.text.toString()
        val dosage = edtDosage.text.toString()
        if (medicineName.isNotEmpty() && dosage.isNotEmpty()) {
            dbHelper.insertData(medicineName, dosage)
            Toast.makeText(this, "Record Added Successfully", Toast.LENGTH_LONG).show()
            clearFields()
            readData()
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateData() {
        val id = selectedEntryId
        if (id != -1) {
            val newMedicineName = edtMedicineName.text.toString()
            val newDosage = edtDosage.text.toString()
            if (newMedicineName.isNotEmpty() && newDosage.isNotEmpty()) {
                dbHelper.updateData(id, newMedicineName, newDosage)
                Toast.makeText(this, "Record Updated Successfully", Toast.LENGTH_LONG).show()
                clearFields()
                readData()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please select an entry to update", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteData() {
        val id = selectedEntryId
        if (id != -1) {
            dbHelper.deleteData(id)
            Toast.makeText(this, "Record Deleted Successfully", Toast.LENGTH_LONG).show()
            clearFields()
            readData()
        } else {
            Toast.makeText(this, "Please select an entry to delete", Toast.LENGTH_SHORT).show()
        }
    }

    private fun readData() {
        val cursor = dbHelper.readData()
        val fromColumns = arrayOf(COLUMN_MEDICINE_NAME, COLUMN_DOSAGE)
        val toViews = intArrayOf(android.R.id.text1, android.R.id.text2)
        val adapter = SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, fromColumns, toViews, 0)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val cursor = parent.getItemAtPosition(position) as Cursor
            selectedEntryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            edtMedicineName.setText(
                cursor.getString(
                    cursor.getColumnIndexOrThrow(
                        COLUMN_MEDICINE_NAME
                    )
                )
            )
            edtDosage.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOSAGE)))
        }
    }

    private fun clearFields() {
        edtMedicineName.text.clear()
        edtDosage.text.clear()
    }
}
