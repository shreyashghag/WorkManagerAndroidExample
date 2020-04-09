package com.android.workmanagerexample.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.workmanagerexample.MainActivity
import com.android.workmanagerexample.R


class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
){

    val WORK_RESULT = "work_result";
    override fun doWork(): Result {
    val taskData=inputData
        val taskDataString = taskData.getString(MainActivity.MESSAGE_STATUS);
        showNotification(
            "WorkManager",
            taskDataString ?: "Message has been Sent"
        )
        val outputData = Data.Builder().putString(WORK_RESULT, "Jobs Finished").build();
        return Result.success(outputData)
    }

    fun showNotification(task:String, desc:String){

        val manager = getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "task_channel";
        val channelName = "task_name";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        val  builder =  NotificationCompat.Builder(getApplicationContext(), channelId)
            .setContentTitle(task)
            .setContentText(desc)
            .setSmallIcon(R.mipmap.ic_launcher);
        manager.notify(1, builder.build());
    }
}