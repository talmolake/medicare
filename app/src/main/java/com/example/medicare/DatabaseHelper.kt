package com.example.medicare

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME " +
                "($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_NAME TEXT, " +
                "$COL_EMAIL TEXT, " +
                "$COL_PASSWORD TEXT)"
        db.execSQL(createTable)

        val createTableReservation = "CREATE TABLE $TABLE_NAME_RESERVATION " +
                "($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_DATE TEXT, " +
                "$COL_TIME TEXT," +
                "$COL_DOCTOR_NAME TEXT, " +
                "$COL_LOCATION TEXT)" // Add the location_name column here
        db.execSQL(createTableReservation)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_RESERVATION")
        onCreate(db)
    }

    fun insertData(name: String, email: String, password: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, name)
        contentValues.put(COL_EMAIL, email)
        contentValues.put(COL_PASSWORD, password)
        return db.insert(TABLE_NAME, null, contentValues)
    }

    fun readUser(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    @SuppressLint("Range")
    fun getUserByEmail(email: String): DatabaseContract? {
        val db = this.readableDatabase
        var user: DatabaseContract? = null
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_EMAIL = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(email))
        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(COL_ID)
            val nameIndex = cursor.getColumnIndex(COL_NAME)
            val emailIndex = cursor.getColumnIndex(COL_EMAIL)
            val passwordIndex = cursor.getColumnIndex(COL_PASSWORD)

            if (idIndex != -1 && nameIndex != -1 && emailIndex != -1 && passwordIndex != -1) {
                val id = cursor.getLong(idIndex)
                val name = cursor.getString(nameIndex)
                val email = cursor.getString(emailIndex)
                val password = cursor.getString(passwordIndex)
                user = DatabaseContract(id, name, email, password)
            } else {
                // Handle case where column index is -1
                // Log an error or show a message
                Log.e("DatabaseHelper", "Column not found in cursor")
            }
        }
        cursor.close()
        db.close()
        return user
    }

    fun updateUser(oldEmail: String, newName: String, newEmail: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL_NAME, newName)
            put(COL_EMAIL, newEmail)
        }
        val selection = "$COL_EMAIL = ?"
        val selectionArgs = arrayOf(oldEmail)
        val updatedRows = db.update(TABLE_NAME, contentValues, selection, selectionArgs)
        db.close()
        return updatedRows > 0
    }

    fun insertReservation(date: String, time: String, doctor_name: String, location_name: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL_DATE, date)
            put(COL_TIME, time)
            put(COL_DOCTOR_NAME, doctor_name)
            put(COL_LOCATION, location_name)
        }
        return db.insert(TABLE_NAME_RESERVATION, null, contentValues)
    }

    fun readReservations(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME_RESERVATION", null)
    }

    @SuppressLint("Range")
    fun getAllReservations(): ArrayList<DatabaseContract.ReservationEntry> {
        val reservations = ArrayList<DatabaseContract.ReservationEntry>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME_RESERVATION"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE))
                val time = cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME))
                val doctor_name = cursor.getString(cursor.getColumnIndexOrThrow(COL_DOCTOR_NAME))
                val location_name = cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCATION))

                val reservation = DatabaseContract.ReservationEntry(id, date, time, doctor_name, location_name)
                reservations.add(reservation)
            } while (cursor.moveToNext())
        } else {
            Log.e("DatabaseHelper", "Column not found in cursor")
        }
        cursor.close()
        db.close()
        return reservations
    }

    fun deleteReservation(date: String, time: String): Boolean {
        val db = this.writableDatabase
        val whereClause = "$COL_DATE = ? AND $COL_TIME = ?"
        val whereArgs = arrayOf(date, time)
        val deletedRows = db.delete(TABLE_NAME_RESERVATION, whereClause, whereArgs)
        db.close()
        return deletedRows > 0
    }

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "RegistrationDB"
        private const val TABLE_NAME = "registration"
        private const val TABLE_NAME_RESERVATION = "reservation"
        private const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_EMAIL = "email"
        private const val COL_PASSWORD = "password"
        const val COL_DATE = "date"
        const val COL_TIME = "time"
        const val COL_DOCTOR_NAME = "doctor_name"
        const val COL_LOCATION = "location_name"
    }
}
