package com.example.medicare

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageButton
import com.example.medicare.databinding.DashboardlayoutBinding

class dashboardLayout : BaseActivity() {

    private lateinit var button: ImageButton
    private lateinit var binding: DashboardlayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DashboardlayoutBinding.inflate(layoutInflater)
        val contentFrame = findViewById<FrameLayout>(R.id.content_frame)
        contentFrame.addView(binding.root)

        val username = intent.getStringExtra("username")

        // Display a greeting message on the dashboard
        binding.greetingTextView.text = "Hello, $username!"

        binding.all.setOnClickListener {
            // Create an intent to navigate to the target activity
            val intent = Intent(this, DoctorsPage::class.java)
            startActivity(intent)
        }
    }
}
