package com.example.todotask.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todotask.data.Task

class DataBaseManager(context:Context):SQLiteOpenHelper(context,
    DATABASE_NAME,null,
    DATABASE_VERSION
) {
    companion object {
        const val DATABASE_VERSION = 4
        const val DATABASE_NAME = "DTtoDoList.db"

         private const val SQL_CREATE_TABLE =
        "CREATE TABLE ${Task.TABLE_NAME}(" +
                "${Task.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Task.COLUMN_NAME} TEXT," +
                "${Task.COLUMN_DESCRIPTION} TEXT," +
                "${Task.COLUMN_HOUR} TEXT," +
                "${Task.COLUMN_DONE} INTEGER)"

        private const val SQL_DELETE_TABLE ="DROP TABLE IF EXISTS Task"
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        onDelete(db)
        onCreate(db)
    }

    private fun onDelete(db:SQLiteDatabase)
    {
        db.execSQL(SQL_DELETE_TABLE)
    }


}