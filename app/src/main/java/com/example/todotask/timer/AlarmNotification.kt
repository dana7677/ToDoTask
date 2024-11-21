package com.example.todotask.timer



import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.todotask.add_activity


class AlarmNotification:BroadcastReceiver() {

    var notificationID:Int=0
    var tittletaskString:String=""
    var descripttaskString:String=""

    override fun onReceive(context: Context, p1: Intent?) {

        if(p1!=null) {
            var titleTask = p1.extras?.getString("Tittle_Task")
            if(titleTask!=null){
                tittletaskString=titleTask
            }
            var descriptTask = p1.extras?.getString("Descript_Task")
            if(descriptTask!=null){
                descripttaskString=descriptTask
            }
        }
        createSimpleNotification(context)
    }

    private fun createSimpleNotification(context: Context) {
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)



        val intent = Intent(context, add_activity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }


        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_MUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, notificationID, intent, flag)



        val notification = NotificationCompat.Builder(context, add_activity.MY_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_search)
            .setContentTitle(tittletaskString)
            .setContentText("Task To Do")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(descripttaskString)
            )
            .setLights(Color.BLUE, 1, 1)
            .setVibrate(longArrayOf(500, 500, 500, 500, 500, 500, 500, 500, 500))
            .setSound(alarmSound)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }
}