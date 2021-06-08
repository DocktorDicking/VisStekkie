package com.example.visstekkie

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHandler(context: Context,
                name: String?,
                factory: SQLiteDatabase.CursorFactory?,
                version: Int) : SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_STEKKIES_TABLE = ("CREATE TABLE $TABLE_STEKKIES ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NAME TEXT, "
                + "$COLUMN_DESCRIPTION TEXT, "
                + "$COLUMN_IMAGEPATH TEXT, "
                + "$COLUMN_LATITUDE REAL, "
                + "$COLUMN_LONGITUDE REAL)")
        db.execSQL(CREATE_STEKKIES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STEKKIES)
        onCreate(db)
    }

    fun addStekkie(stekkie: StekkieModel) {
        val values = getContentValues(stekkie)
        val db = this.writableDatabase
        db.insert(TABLE_STEKKIES, null, values)
        db.close()
    }

    fun updateStekkie(stekkie: StekkieModel) {
        val values = getContentValues(stekkie)
        val db = this.writableDatabase
        db.update(TABLE_STEKKIES, values, "$COLUMN_ID = ? ", arrayOf(stekkie.id.toString()))
        db.close()
    }

    private fun getContentValues(stekkie: StekkieModel): ContentValues {
        val values = ContentValues()
        values.put(COLUMN_NAME, stekkie.name)
        values.put(COLUMN_DESCRIPTION, stekkie.description)
        values.put(COLUMN_IMAGEPATH, stekkie.imagePath)
        values.put(COLUMN_LATITUDE, stekkie.latitude)
        values.put(COLUMN_LONGITUDE, stekkie.longitude)
        return values
    }

    fun getStekkies(): ArrayList<StekkieModel> {
        val db = this.writableDatabase
        var list = ArrayList<StekkieModel>()
        val query = "SELECT * FROM $TABLE_STEKKIES"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                var stekkie = StekkieModel(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_IMAGEPATH)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE))
                )
                list.add(stekkie)
                cursor.moveToNext()
            }
            cursor.close()
        }
        db.close()
        return list
    }

    fun deleteStekkie(id: Int): Boolean {
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_STEKKIES WHERE $COLUMN_ID = $id"
        val cursor = db.rawQuery(query,null)

        if (cursor.moveToFirst()) {
            db.delete(TABLE_STEKKIES, COLUMN_ID + " = ?", arrayOf(id.toString()))
            cursor.close()
            return true
        }
        db.close()
        return false
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "stekkie.db"
        val TABLE_STEKKIES = "stekkies"

        val COLUMN_ID = "_id"
        val COLUMN_NAME = "name"
        val COLUMN_DESCRIPTION = "description"
        val COLUMN_IMAGEPATH = "imagepath"
        val COLUMN_LATITUDE = "latitude"
        val COLUMN_LONGITUDE = "longitude"
    }
}