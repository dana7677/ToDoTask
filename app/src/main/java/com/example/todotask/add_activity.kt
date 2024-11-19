package com.example.todotask

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todotask.data.Task
import com.example.todotask.data.providers.TaskDAO
import com.example.todotask.databinding.ActivityAddBinding

class add_activity : AppCompatActivity() {


    lateinit var bindingMainActivity:ActivityAddBinding
    lateinit var editableNameText: EditText
    lateinit var editableDescriptText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        bindingMainActivity = ActivityAddBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        val taskDAO = TaskDAO(this)
        //taskDAO.insert(Task(-1, "Limpiar el coche", "primero se utiliza champu",false))
        val task = taskDAO.findByID(1)!!
        task.done = true

        taskDAO.deleteAll()
        //taskDAO.delete(task)

        val taskList = taskDAO.findAll()
        for (task in taskList) {
            println(task)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        onCreate()


    }



    private fun onCreate()
    {
        println("He pasado por aqui")
        bindingMainActivity.addButton.setOnClickListener {
            println("He pasado por aqui 2")

            editableNameText=bindingMainActivity.nameTaskText
            editableDescriptText=bindingMainActivity.descriptText
            val taskDAO = TaskDAO(this)
            taskDAO.insert(Task(-1, editableNameText.getText().toString(), editableDescriptText.getText().toString(),false))

            val taskList = taskDAO.findAll()
            for (task in taskList) {
                println(task)
            }


        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            android.R.id.home->
            {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}