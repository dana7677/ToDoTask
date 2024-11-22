package com.example.todotask

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todotask.data.Task
import com.example.todotask.data.providers.TaskDAO
import com.example.todotask.databinding.ActivityAddBinding
import com.example.todotask.timer.AlarmNotification
import com.example.todotask.timer.TimerPickerFragment
import java.util.Calendar
import java.util.TimeZone


class add_activity : AppCompatActivity() {


    companion object{
        const val MY_CHANNEL_ID="myChannel"
    }


        var taskID:Int=0
    var totalTime:Int=0
    lateinit var bindingMainActivity:ActivityAddBinding
    lateinit var editableNameText: EditText
    lateinit var editableDescriptText: EditText
    lateinit var horaTxt:String
    lateinit var checkBoxAlarm:CheckBox

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



        var size=taskDAO.findAll().size + 1

        taskID=size


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        onCreateoptions()


    }

    private fun onPressedAddButton()
    {
        val taskDAO = TaskDAO(this)


        if(bindingMainActivity.CheckedAlarm.isChecked==true)
        {
            scheduleNotification(totalTime)

            taskDAO.insert(Task(-1, editableNameText.getText().toString(), editableDescriptText.getText().toString(),horaTxt,false))
        }
        else
        {
            taskDAO.insert(Task(-1, editableNameText.getText().toString(), editableDescriptText.getText().toString(),"",false))
        }
        finish()

    }



    private fun onCreateoptions()
    {
        val taskDAO = TaskDAO(this)

        bindingMainActivity.addButton.setOnClickListener {

            onPressedAddButton()

        }


        editableNameText=bindingMainActivity.nameTaskText
        editableDescriptText=bindingMainActivity.TextInputValue

        //Print
        val taskList = taskDAO.findAll()
        for (task in taskList) {
            println(task)
        }



        bindingMainActivity.butttonAlarma.setOnClickListener {
            setPermissionNotification()
            showTimeDailog()
        }


    }

    private fun setPermissionNotification()
    {
        //Checking Permission of Notification == TRUE
        val permissionState =
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        // If the permission is not granted, request it.
        // If the permission is not granted, request it.
        if (permissionState == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }

    }

    private fun scheduleNotification(timeAlarm:Int) {


        //Creating a notification channel
        val channel = NotificationChannel(MY_CHANNEL_ID, "Recordatorios", NotificationManager.IMPORTANCE_HIGH)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        //Seteamos Notifiacion ID
       // taskID=bindingMainActivity.nameTaskText.toString()

        val intent = Intent(applicationContext, AlarmNotification::class.java)

        intent.putExtra("Tittle_Task",editableNameText.getText().toString())
        intent.putExtra("Descript_Task",editableDescriptText.getText().toString())


        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            taskID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager


        //For Testing 10 sec outs notification

        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().timeInMillis + 10000, pendingIntent)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().timeInMillis + timeAlarm, pendingIntent)





    }


    private fun showTimeDailog()
    {

        val timerPicker=TimerPickerFragment{onTimeSelected(it)}
        timerPicker.show(supportFragmentManager,"time")

    }
    private fun onTimeSelected(time:String)
    {


        horaTxt=time

        bindingMainActivity.butttonAlarma.setText(time)

        val words = time.split(":")

        var hourFirst=words[0].toInt()
        var minutesFirst=words[1].toInt()

        var totalTimepass=  minutesFirst * 60000

        println(hourFirst)
        var Hours=0

        var calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"))


        if(calendar.get(Calendar.HOUR_OF_DAY) >hourFirst)
        {

            //For testing
            //Toast.makeText(this, "${calendar.get(Calendar.HOUR_OF_DAY)}", Toast.LENGTH_SHORT).show()


            Hours=(hourFirst+24)-calendar.get(Calendar.HOUR_OF_DAY)

        }
        else
        {
            Hours=hourFirst-calendar.get(Calendar.HOUR_OF_DAY)
        }

        println(Hours)

        Hours=Hours*60//Para sacar los minutos

        Hours=Hours*60000 //Para sacar los milisegundos

        totalTimepass=totalTimepass+Hours //Sumando las horas y los minutos


        totalTime=totalTimepass

        //Seteando la alarma con sus horas
        //DEBUG
        println(Hours)
        println(totalTimepass)

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

    private fun playRingTone()
    {
        try{
            val soundPrueba = Uri.parse("android.resource://${this.packageName}/raw/cs_bomb")

            MediaPlayer.create(this,soundPrueba).start()

        }catch (e:Exception)
        {
            Toast.makeText(this,"Failed To Load Sound",Toast.LENGTH_SHORT).show()
        }
    }

}