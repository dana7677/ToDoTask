package com.example.todotask.data

data class Task(val id:Long,var name:String,var descript:String,var done:Boolean)

{
    companion object
    {
        const val TABLE_NAME="Task"
        const val COLUMN_ID="id"
        const val COLUMN_NAME="Name"
        const val COLUMN_DESCRIPTION="Description"
        const val COLUMN_DONE="Done"

    }
}