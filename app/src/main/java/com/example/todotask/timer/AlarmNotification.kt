package com.example.todotask.timer



import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.todotask.R
import com.example.todotask.add_activity


class AlarmNotification:BroadcastReceiver() {

    var notificationID:Int=0
    var tittletaskString:String=""
    var descripttaskString:String=""
    lateinit var uriNewtry:Uri

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


        //Dos Soniditos wenos para asustar al personal.

        val soundUri2 = Uri.parse("android.resource://${context.packageName}/raw/nokia_loud")

        MediaPlayer.create(context,soundUri2).start()

        val soundUri = Uri.parse("android.resource://${context.packageName}/raw/nokia_arabic")

        MediaPlayer.create(context,soundUri).start()


        val intent = Intent(context, add_activity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }


        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_MUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, notificationID, intent, flag)



        val channelId = add_activity.MY_CHANNEL_ID
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Default Channel", NotificationManager.IMPORTANCE_HIGH).apply {
                description = "This is the channel for task notifications"


                val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM) // Esto es importante para que se reproduzca como sonido de alarma
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION) // El tipo de contenido
                    .build()

                // Establecer el sonido para el canal usando AudioAttributes
                setSound(soundUri, audioAttributes)
            }
            notificationManager.createNotificationChannel(channel)
        }




        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_menu_search)
            .setContentTitle(tittletaskString)
            .setContentText("Task To Do")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(descripttaskString)
            )
            .setLights(Color.BLUE, 1, 1)
            .setVibrate(longArrayOf(500, 500, 500, 500, 500, 500, 500, 500, 500))
            .setContentIntent(pendingIntent)
            .setSound(soundUri)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }
}