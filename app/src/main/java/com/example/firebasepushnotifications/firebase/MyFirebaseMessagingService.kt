package com.example.firebasepushnotifications.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.firebasepushnotifications.MainActivity
import com.example.firebasepushnotifications.R
import com.example.firebasepushnotifications.firebase.constants.Constants.CHANNEL_ID
import com.example.firebasepushnotifications.firebase.constants.Constants.CHANNEL_NAME
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    //generate notification
    fun generateNotifications(title : String , description : String){
        //intent to enable users move from notifications received to app
        val intent = Intent(this, MainActivity::class.java)
        //clear all activities and put main activity at the top
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        //token to give to home screen to perform an action on our apps' behalf
        val pendingIntent = PendingIntent.getActivity(
            this,0, intent, PendingIntent.FLAG_ONE_SHOT
        )

        var builder : NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext, CHANNEL_ID
        )
            .setSmallIcon(R.drawable.autochek_logo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        // attach builder to notification layout
        builder = builder.setContent(getRemoteView(title, description))

        //create notification manager
        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())
    }

    private fun getRemoteView(title: String, description: String): RemoteViews {
        val remoteViews = RemoteViews(
            "com.example.firebasepushnotifications.firebase", R.layout.notifications
        )

        remoteViews.setTextViewText(R.id.titleId, title)
        remoteViews.setTextViewText(R.id.descriptionId, description)
        remoteViews.setImageViewResource(R.id.imageView, R.drawable.autochek_logo)

        return remoteViews
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null){
            generateNotifications(
                remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!
            )
        }
    }
}