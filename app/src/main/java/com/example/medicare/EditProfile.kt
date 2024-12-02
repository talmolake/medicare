package com.example.medicare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import com.example.medicare.databinding.EditProfileBinding

class EditProfile : BaseActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var binding: EditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditProfileBinding.inflate(layoutInflater)
        val contentFrame = findViewById<FrameLayout>(R.id.content_frame)
        contentFrame.addView(binding.root)

        databaseHelper = DatabaseHelper(this)

        val saveButton = findViewById<Button>(R.id.saveButton)
        val userNameEditText = findViewById<EditText>(R.id.userNameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)

        val userEmail = intent.getStringExtra("userEmail")
        if (userEmail == null) {
            Toast.makeText(this, "Invalid user email", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val user = databaseHelper.getUserByEmail(userEmail)
        if (user != null) {
            userNameEditText.setText(user.name)
            emailEditText.setText(user.email)
        } else {
            Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        saveButton.setOnClickListener {
            val newUsername = userNameEditText.text.toString().trim()
            val newEmail = emailEditText.text.toString().trim()

            if (newUsername.isNotEmpty() && newEmail.isNotEmpty()) {
                val updated = databaseHelper.updateUser(userEmail, newUsername, newEmail)
                if (updated) {
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Settings_Page::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Username and email cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

