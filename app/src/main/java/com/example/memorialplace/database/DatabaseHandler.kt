package com.example.memorialplace.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.memorialplace.models.MemorialPlaceModel

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MemorialPlacesDatabase"
        private const val TABLE_MEMORIAL_PLACE = "MemorialPlacesTable"

        private const val KEY_ID = "_id"
        private const val KEY_TITLE = "title"
        private const val KEY_IMAGE = "image"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_DATE = "date"
        private const val KEY_LOCATION = "location"
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_MEMORIAL_PLACE_TABLE = ("CREATE TABLE " + TABLE_MEMORIAL_PLACE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_LOCATION + " TEXT,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT)")
        db?.execSQL(CREATE_MEMORIAL_PLACE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_MEMORIAL_PLACE")
        onCreate(db)
    }

    fun addMemorialPlace(memorialPlace: MemorialPlaceModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, memorialPlace.title) // HappyPlaceModelClass TITLE
        contentValues.put(KEY_IMAGE, memorialPlace.image) // HappyPlaceModelClass IMAGE
        contentValues.put(
            KEY_DESCRIPTION,
            memorialPlace.description
        ) // HappyPlaceModelClass DESCRIPTION
        contentValues.put(KEY_DATE, memorialPlace.date) // HappyPlaceModelClass DATE
        contentValues.put(KEY_LOCATION, memorialPlace.location) // HappyPlaceModelClass LOCATION
        contentValues.put(KEY_LATITUDE, memorialPlace.latitude) // HappyPlaceModelClass LATITUDE
        contentValues.put(KEY_LONGITUDE, memorialPlace.longitude) // HappyPlaceModelClass LONGITUDE

        // Inserting Row
        val result = db.insert(TABLE_MEMORIAL_PLACE, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return result
    }

    @SuppressLint("Range")
    fun getMemorialPlacesList(): ArrayList<MemorialPlaceModel> {
        val memorialPlaceList = ArrayList<MemorialPlaceModel>()
        val selectQuery = "SELECT * FROM $TABLE_MEMORIAL_PLACE"
        val db = this.readableDatabase

        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val place = MemorialPlaceModel(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                        cursor.getString(cursor.getColumnIndex(KEY_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                        cursor.getString(cursor.getColumnIndex(KEY_LOCATION)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_LONGITUDE)),
                    )
                    memorialPlaceList.add(place)
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        return memorialPlaceList
    }

    fun updateMemorialPlace(happyPlace: MemorialPlaceModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, happyPlace.title) // HappyPlaceModelClass TITLE
        contentValues.put(KEY_IMAGE, happyPlace.image) // HappyPlaceModelClass IMAGE
        contentValues.put(
            KEY_DESCRIPTION,
            happyPlace.description
        ) // HappyPlaceModelClass DESCRIPTION
        contentValues.put(KEY_DATE, happyPlace.date) // HappyPlaceModelClass DATE
        contentValues.put(KEY_LOCATION, happyPlace.location) // HappyPlaceModelClass LOCATION
        contentValues.put(KEY_LATITUDE, happyPlace.latitude) // HappyPlaceModelClass LATITUDE
        contentValues.put(KEY_LONGITUDE, happyPlace.longitude) // HappyPlaceModelClass LONGITUDE

        // Updating Row
        val success =
            db.update(TABLE_MEMORIAL_PLACE, contentValues, KEY_ID + "=" + happyPlace.id, null)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    fun deleteMemorialPlace(memorialPlace: MemorialPlaceModel) : Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_MEMORIAL_PLACE, KEY_ID + "=" + memorialPlace.id, null)
        db.close()
        return success
    }


}