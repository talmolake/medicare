package com.example.medicare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Welcome_Page :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_page)

        val registrationButton: Button = findViewById(R.id.registration_button)
        val loginButton: Button = findViewById(R.id.login_button)

        // Set click listeners for buttons
        registrationButton.setOnClickListener {
            // Navigate to registration activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            // Navigate to login activity
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}