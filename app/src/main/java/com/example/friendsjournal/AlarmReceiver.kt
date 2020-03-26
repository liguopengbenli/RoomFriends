package com.example.friendsjournal
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

private const val LOG_TAG = "AlarmReceiver"
private const val channelID = "AlarmReceiverID"
private lateinit var builder: NotificationCompat.Builder

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var alarmContext:Context
    override fun onReceive(context: Context, intent: Intent) {
        alarmContext = context
        if (intent.action == "android.intent.action.BOOT_COMPLETED")
        {
            Log.d("BOOT receiver","SetAlarm2 init")
            setAlarm()
            Toast.makeText(alarmContext, "Set Alarm for notify your friends", Toast.LENGTH_LONG).show();
        }else{
            createNotificationChannel()
            launchNotification()
            Log.d(LOG_TAG,"SetAlarm2 receive")
            Toast.makeText(alarmContext, "Time to connect your friends: $recentFriendName", Toast.LENGTH_LONG).show();
        }
    }

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    var recentFriendName = ""

    private fun setAlarm(){
        Log.d(LOG_TAG,"SetAlarm2  recentFriendName = $recentFriendName et context = $alarmContext")
        try {
            val beginAt  = SystemClock.elapsedRealtime() + 60 * 1000
            val interval = getInterval()

            alarmMgr = alarmContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent = Intent(alarmContext, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(alarmContext, 0, intent, 0)
            }
            alarmMgr?.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                beginAt,
                interval, // 1 heure
                alarmIntent
            )
            Log.d(
                LOG_TAG,
                 "SetAlarm2 the Alarm has been configured successfully"
            )
        } catch (e: Exception) {
            Log.d(
                LOG_TAG,
                 "SetAlarm2 an exception has ocurred while setting the Alarm..."
            )
            e.printStackTrace()
        }
    }

    private fun createNotificationChannel() {
        builder = NotificationCompat.Builder(alarmContext, channelID)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
            .setContentTitle("Remember")
            .setContentText("Time to connect your friends")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(LOG_TAG,"SetAlarm2 create notification")
            val name = alarmContext.resources.getString(R.string.channel_name)
            val descriptionText = alarmContext.resources.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = (alarmContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun launchNotification(){
        try {
            with(NotificationManagerCompat.from(alarmContext)) {
                // notificationId is a unique int for each notification that you must define
                notify(1, builder.build())
            }
        }catch (e: Exception){
            Log.e(
                LOG_TAG,
                "SetAlarm2 an exception has ocurred while running the Alarm..."
            )
        }
    }

    private fun getInterval(): Long {
        var interval = 1000*60L //1min
        try {
            intervalNotifPref = alarmContext.getSharedPreferences(PREFS_FILENAME, 0);
            interval = intervalNotifPref.getLong(PREFS_FILEVAL, 1000 * 60L)
            Log.i(
                LOG_TAG,
                "getInterval with interval = $interval"
            )
        }catch (e: Exception){
            Log.e(
                LOG_TAG,
                "getInterval exception with interval = $interval"
            )
        }
        return interval
    }


}
