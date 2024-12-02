package com.example.medicare

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val TABLE_NAME = "prescriptions"
const val COLUMN_ID = "ID"
const val COLUMN_MEDICINE_NAME = "medicine_name"
const val COLUMN_DOSAGE = "dosage"

class PrescriptionDBHelper(context: Context) : SQLiteOpenHelper(context, "medicareDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_MEDICINE_NAME TEXT,
                $COLUMN_DOSAGE TEXT
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(medicineName: String, dosage: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_MEDICINE_NAME, medicineName)
            put(COLUMN_DOSAGE, dosage)
        }
        return db.insert(TABLE_NAME, null, contentValues)
    }

    fun readData(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT $COLUMN_ID as _id, * FROM $TABLE_NAME", null)
    }

    fun updateData(id: Int, newMedicineName: String, newDosage: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_MEDICINE_NAME, newMedicineName)
            put(COLUMN_DOSAGE, newDosage)
        }
        return db.update(TABLE_NAME, contentValues, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun deleteData(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
    }
}
