package com.example.todotask

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todotask.data.Task
import com.example.todotask.data.providers.TaskDAO

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val taskDAO = TaskDAO(this)
        //taskDAO.insert(Task(-1, "Limpiar el coche", false))
        val task = taskDAO.findByID(1)!!
        task.done = true

        val taskList = taskDAO.findAll()
        for (task in taskList) {
            println(task)
        }

    }
}