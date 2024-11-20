package com.example.todotask

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todotask.adapters.TaskAdapter
import com.example.todotask.data.Task
import com.example.todotask.databinding.ActivityMainBinding
import com.example.todotask.data.providers.TaskDAO
import com.example.todotask.databinding.ActivityAddBinding

class MainActivity : AppCompatActivity() {



    lateinit var bindingMainActivity: ActivityMainBinding
    lateinit var adapter: TaskAdapter
    lateinit var taskList:List<Task>
    lateinit var buttonMenu:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)

        val taskDAO = TaskDAO(this)


        taskList=taskDAO.findAll()

        adapter = TaskAdapter(taskList){

            //Cuando se de click al checkbox se ejecutara este codigo
            val task = taskList[it]
            task.done = !task.done //Si es + -> - y al reves - -> +
            taskDAO.update(task)   //Updatear los valores
            adapter.updateItems(taskList)
        }


        bindingMainActivity.recyclerTask.adapter=adapter
        bindingMainActivity.recyclerTask.layoutManager = GridLayoutManager(this,1)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bindingMainActivity.addTaskButton.setOnClickListener {

            val intent=Intent(this,add_activity::class.java)

            startActivity(intent)

        }
        bindingMainActivity.clearButton.setOnClickListener {

            taskDAO.deleteCompleteTask()
            taskList=taskDAO.findAll()
            adapter.updateItems(taskDAO.findAll())

        }

        /* Infoooo

            taskDAO.insert(Task(-1, "Limpiar el coche", "primero se utiliza champu",false))
            val task = taskDAO.findByID(1)!!

            task.done = true

            taskDAO.deleteAll()
            taskDAO.delete(task)

            val taskList = taskDAO.findAll()
            for (task in taskList) {
            println(task)
             }

         */

    }

    override fun onResume() {

        adapter.notifyDataSetChanged()

        super.onResume()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        menuInflater.inflate(R.menu.menu_main_activity,menu)

        val menuItem=menu?.findItem(R.id.menubutton)!!
        buttonMenu= menuItem.actionView as AppCompatButton

        buttonMenu.setOnClickListener{

            val intent=Intent(this,add_activity::class.java)

            startActivity(intent)
        }

        return true
    }

}