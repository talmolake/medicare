package com.example.medicare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.medicare.databinding.DoctorsPageBinding
import com.example.medicare.databinding.SettingsBinding

class Settings_Page : BaseActivity() {

    private lateinit var binding: SettingsBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var editButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsBinding.inflate(layoutInflater)
        val contentFrame = findViewById<FrameLayout>(R.id.content_frame)
        contentFrame.addView(binding.root)

        databaseHelper = DatabaseHelper(this)

        usernameTextView = findViewById(R.id.username)
        emailTextView = findViewById(R.id.useremail)
        editButton = findViewById(R.id.edit_button)

        val notificationsButton = findViewById<Button>(R.id.notifications_button)
        notificationsButton.setOnClickListener {
            // Handle notifications button click
        }

        val darkThemeButton = findViewById<Button>(R.id.dark_theme_button)
        darkThemeButton.setOnClickListener {
            // Handle dark theme button click
            toggleDarkMode()
        }

        val aboutMedicare = findViewById<TextView>(R.id.about_medicare)
        aboutMedicare.setOnClickListener {
            // Handle about MEDICARE button click
        }

        val prescriptionsAndMedicines = findViewById<TextView>(R.id.prescriptions_and_medicine)
        prescriptionsAndMedicines.setOnClickListener {
            val intent = Intent(this, PrescriptionsAndMedicines::class.java)
            startActivity(intent)
        }


        loadThemePreference()
        loadUserDetails()

        editButton.setOnClickListener {
            val userEmail = emailTextView.text.toString()
            val intent = Intent(this, EditProfile::class.java).apply{
                putExtra("userEmail", userEmail)
            }
            startActivity(intent)
        }

    }


    private fun loadUserDetails() {
        val cursor = databaseHelper.readUser()
        if (cursor.moveToFirst()) {
            val usernameIndex = cursor.getColumnIndex(DatabaseHelper.COL_NAME)
            val emailIndex = cursor.getColumnIndex(DatabaseHelper.COL_EMAIL)
            if (usernameIndex != -1 && emailIndex != -1) {
                val username = cursor.getString(usernameIndex)
                val email = cursor.getString(emailIndex)
                usernameTextView.text = username
                emailTextView.text = email
            }
        } else {
            Log.e("Settings_Page", "No user found in the database")
        }
        cursor.close()
    }

    private fun toggleDarkMode() {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                // Night mode is not active, we're using the light theme
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveThemePreference(true)
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                // Night mode is active, we're using dark theme
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveThemePreference(false)
            }
        }
    }

    private fun saveThemePreference(isDarkMode: Boolean) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("theme_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("dark_mode", isDarkMode)
        editor.apply()
    }

    private fun loadThemePreference() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("theme_preferences", Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
