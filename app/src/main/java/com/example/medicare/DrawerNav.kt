//// DrawerNav.kt
//package com.example.medicare
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.ImageButton
//import androidx.appcompat.app.AppCompatActivity
//import androidx.drawerlayout.widget.DrawerLayout
//import com.google.android.material.navigation.NavigationView
//
//class DrawerNav : AppCompatActivity() {
//    private lateinit var drawerLayout: DrawerLayout
//    private lateinit var navigationView: NavigationView
//    private lateinit var menuButton: ImageButton
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_drawer_nav)
//
//        drawerLayout = findViewById(R.id.drawer_layout)
//        navigationView = findViewById(R.id.nav_view)
//        menuButton = findViewById(R.id.menuButton)
//
//        menuButton.setOnClickListener {
//            toggleDrawer()
//        }
//
//        navigationView.setNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.book_appointments -> {
//                    startActivity(Intent(this@DrawerNav, DoctorsPage::class.java))
//                    true
//                }
//                R.id.view_appointments -> {
//                    startActivity(Intent(this@DrawerNav, Appointments_list::class.java))
//                    true
//                }
//                R.id.choosecategoreies -> {
//                    startActivity(Intent(this@DrawerNav, dashboardLayout::class.java))
//                    true
//                }
//                R.id.settings -> {
//                    startActivity(Intent(this@DrawerNav, Settings_Page::class.java))
//                    true
//                }
//                else -> false
//            }
//        }
//    }
//
//    override fun onBackPressed() {
//        if (drawerLayout.isDrawerOpen(navigationView)) {
//            drawerLayout.closeDrawer(navigationView)
//        } else {
//            super.onBackPressed()
//        }
//    }
//
//    private fun toggleDrawer() {
//        if (drawerLayout.isDrawerOpen(navigationView)) {
//            drawerLayout.closeDrawer(navigationView)
//        } else {
//            drawerLayout.openDrawer(navigationView)
//        }
//    }
//}
