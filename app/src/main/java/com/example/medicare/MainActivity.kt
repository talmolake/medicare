package com.example.medicare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SQLite database helper
        dbHelper = DatabaseHelper(this)

        // Initialize views
        nameEditText = findViewById(R.id.name)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        confirmPasswordEditText = findViewById(R.id.confirmPassword)
        registerButton = findViewById(R.id.register_button)


        // Set click listener for register button
        registerButton.setOnClickListener {
            // Check if all fields are filled
            if (nameEditText.text.toString().isEmpty() ||
                emailEditText.text.toString().isEmpty() ||
                passwordEditText.text.toString().isEmpty() ||
                confirmPasswordEditText.text.toString().isEmpty()
            ) {
                // Show a toast message asking the user to fill in all details
                Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show()
            } else {
                // Check if password and confirm password match
                if (passwordEditText.text.toString() == confirmPasswordEditText.text.toString()) {
                    // Passwords match, proceed with registration
                    val name = nameEditText.text.toString()
                    val email = emailEditText.text.toString()
                    val password = passwordEditText.text.toString()

                    // Save user details to SQLite database
                    val newRowId = dbHelper.insertData(name, email, password)
                    val user = dbHelper.getUserByEmail(email)

                    if (newRowId != -1L) {
                        // Registration successful, navigate to dashboard activity
                        val intent = Intent(this, dashboardLayout::class.java)
                        if (user != null) {
                            intent.putExtra("username", user.name)
                            intent.putExtra("email", user.email)
                        }
                        startActivity(intent)
                        finish() // Optional: finish the current activity to prevent going back to it with the back button
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to register", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Passwords do not match, show a toast message
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
