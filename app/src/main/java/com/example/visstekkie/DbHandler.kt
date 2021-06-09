package com.example.visstekkie

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Database handler class to handle all database operations this app needs.
 */
class DbHandler(context: Context,
                name: String?,
                factory: SQLiteDatabase.CursorFactory?,
                version: Int) : SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {

    /**
     * Get's called when there is no database.TABLE_STEKKIES for this app yet.
     */
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

    /**
     * Gets calles when the database version get's changed.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STEKKIES)
        onCreate(db)
    }

    /**
     * Method to add a stekkie to the database.
     */
    fun addStekkie(stekkie: StekkieModel) {
        val values = getContentValues(stekkie)
        val db = this.writableDatabase
        db.insert(TABLE_STEKKIES, null, values)
        db.close()
    }

    /**
     * Updates the given stekkie in the database.
     */
    fun updateStekkie(stekkie: StekkieModel) {
        val values = getContentValues(stekkie)
        val db = this.writableDatabase
        db.update(TABLE_STEKKIES, values, "$COLUMN_ID = ? ", arrayOf(stekkie.id.toString()))
        db.close()
    }

    /**
     * This method creates a ContentValues() object which is used by other methods to save
     * the vales in the database.
     *
     * This method returns a ContentValues() object based on the given stekkie.
     */
    private fun getContentValues(stekkie: StekkieModel): ContentValues {
        val values = ContentValues()
        values.put(COLUMN_NAME, stekkie.name)
        values.put(COLUMN_DESCRIPTION, stekkie.description)
        values.put(COLUMN_IMAGEPATH, stekkie.imagePath)
        values.put(COLUMN_LATITUDE, stekkie.latitude)
        values.put(COLUMN_LONGITUDE, stekkie.longitude)
        return values
    }

    /**
     * Returns all stekkies in the database.
     * This method is used to populate the RecyclerView in the main activity.
     */
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

    /**
     * Removed the given stekkie from the database.
     *
     * //TODO make sure to delete the image file in the private storage.
     */
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

    /**
     * Holds all global values for this dbHandler.
     */
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