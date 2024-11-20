package com.example.todotask

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import com.example.todotask.timer.AlarmNotification
import com.example.todotask.timer.AlarmNotification.Companion.NOTIFICATION_ID
import com.example.todotask.timer.TimerPickerFragment
import java.util.Calendar

class add_activity : AppCompatActivity() {


    companion object{
        const val MY_CHANNEL_ID="myChannel"
    }

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

        /*
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
         */

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        onCreateoptions()


    }



    private fun onCreateoptions()
    {
        println("He pasado por aqui")
        bindingMainActivity.addButton.setOnClickListener {
        println("He pasado por aqui 2")
        }


        editableNameText=bindingMainActivity.nameTaskText
        editableDescriptText=bindingMainActivity.descriptText
        val taskDAO = TaskDAO(this)
        taskDAO.insert(Task(-1, editableNameText.getText().toString(), editableDescriptText.getText().toString(),false))

        //Print
        val taskList = taskDAO.findAll()
        for (task in taskList) {
            println(task)
        }



        bindingMainActivity.butttonAlarma.setOnClickListener {
            showTimeDailog()
        }


    }

    private fun scheduleNotification(timeAlarm:Int) {


        /*
        val intent = Intent(applicationContext, AlarmNotification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().timeInMillis + timeAlarm, pendingIntent)


         */
    }


    private fun showTimeDailog()
    {


        val timerPicker=TimerPickerFragment{onTimeSelected(it)}
        timerPicker.show(supportFragmentManager,"time")




    }
    private fun onTimeSelected(time:String)
    {



        bindingMainActivity.butttonAlarma.setText(time)

        //val modifiedString = time.replace(":", "")

        val words = time.split(":")

        var hourFirst=words[0].toInt()
        var minutesFirst=words[1].toInt()

        var Hours=(Calendar.HOUR_OF_DAY)-hourFirst

      // var valuealarm= modifiedString.toInt()

        //Calendar.HOUR_OF_DAY-
        //println(valuealarm)
        //Hora Seleccionada.

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