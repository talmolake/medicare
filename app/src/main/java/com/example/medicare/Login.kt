package com.example.medicare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class Login : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Add any additional initialization code here
        // Initialize UI elements
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        nameEditText = findViewById(R.id.name)
        loginButton = findViewById(R.id.login_button)

        // Initialize database helper
        dbHelper = DatabaseHelper(this)

        // Set click listener for login button
        loginButton.setOnClickListener {
            // Retrieve user input
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Retrieve user details from the database
            val user = dbHelper.getUserByEmail(email)

            if (user != null && user.password == password) {
                // Credentials are valid, navigate to dashboard activity
                val intent = Intent(this, dashboardLayout::class.java)
                intent.putExtra("username", user.name)
                intent.putExtra("email", user.email)
                startActivity(intent)
                finish() // Optional: finish the current activity to prevent going back to it with the back button
            } else {
                // Credentials are invalid, show an error message
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}