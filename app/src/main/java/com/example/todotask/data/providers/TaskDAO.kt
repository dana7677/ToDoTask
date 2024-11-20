package com.example.todotask.data.providers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import com.example.todotask.data.Task
import com.example.todotask.utils.DataBaseManager

class TaskDAO (val context: Context)
{
    lateinit var db:SQLiteDatabase

    private fun getValues(taskpass:Task):ContentValues
    {

        val valuespass=ContentValues().apply {

            put(Task.COLUMN_NAME,taskpass.name)
            put(Task.COLUMN_DESCRIPTION,taskpass.descript)
            put(Task.COLUMN_DONE, taskpass.done)

        }
        return valuespass
    }

    private fun getProjection(task:Task.Companion):Array<String>
    {
        val projection = arrayOf(Task.COLUMN_ID, Task.COLUMN_NAME,Task.COLUMN_DESCRIPTION, Task.COLUMN_DONE)

        return projection
    }

    private fun returnTask(getcursor:Cursor):Task
    {
        val id = getcursor.getLong(getcursor.getColumnIndexOrThrow(Task.COLUMN_ID))
        val name = getcursor.getString(getcursor.getColumnIndexOrThrow(Task.COLUMN_NAME))
        val descript = getcursor.getString(getcursor.getColumnIndexOrThrow(Task.COLUMN_DESCRIPTION))
        val done = getcursor.getInt(getcursor.getColumnIndexOrThrow(Task.COLUMN_DONE)) != 0

        return Task(id,name,descript,done)
    }

    fun open()
    {
        db=DataBaseManager(context).writableDatabase
    }
    fun close()
    {

        db.close()

    }
    fun insert(task:Task)
    {

        open()
        val values=getValues(task)

        try{
            val id=db.insert(Task.TABLE_NAME,null,values)

        }catch (e:Exception)
        {
            Log.e("DB",e.stackTraceToString())
        }
        finally {
            close()
        }


    }
    fun update(task: Task)
    {
        open()
        val values=getValues(task)

        try{
            val updatedRows=db.update(Task.TABLE_NAME, values, "${Task.COLUMN_ID} = ${task.id}", null)

        }catch (e:Exception)
        {
            Log.e("DB",e.stackTraceToString())
        }
        finally {
            close()
        }

    }
    fun delete(task: Task)
    {
        open()

        try {
            // Delete the existing row, returning the number of affected rows
            val deletedRows = db.delete(Task.TABLE_NAME, "${Task.COLUMN_ID} = ${task.id}", null)
        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()
        }
    }
    fun findByID(id:Long):Task?
    {
        open()

        val projection = getProjection(Task)

        try {
            val cursor = db.query(
                Task.TABLE_NAME,
                projection,
                "${Task.COLUMN_ID}=$id",
                null,
                null,
                null,
                null
            )
            if (cursor.moveToNext()) {

                val task=returnTask(cursor)

                return task
            }
        }catch (e:Exception)
        {
            Log.e("DB",e.stackTraceToString())
        }
        finally {
            close()
        }
        
        return null

    }
    fun findAll():List<Task>
    {

        open()
        var list:MutableList<Task> = mutableListOf()

        val projection = getProjection(Task)

        try {
            val cursor = db.query(
                Task.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            while (cursor.moveToNext()) {

                val task=returnTask(cursor)
                list.add(task)

            }

        }catch (e:Exception)
        {
            Log.e("DB",e.stackTraceToString())
        }
        finally {
            close()
        }

        return list

    }

    fun deleteCompleteTask()
    {
        open()
        try
        {

            val deleteItem=db.delete(Task.TABLE_NAME,"${Task.COLUMN_DONE} = true",null)

        }catch (e:Exception)
        {
            Log.e("DB",e.stackTraceToString())
        }
        finally {
            close()
        }
    }
    fun deleteAll()
    {
        open()
        try
        {
            val deleteItem=db.delete(Task.TABLE_NAME,null,null)

        }catch (e:Exception)
        {
            Log.e("DB",e.stackTraceToString())
        }
        finally {
            close()
        }
    }
}