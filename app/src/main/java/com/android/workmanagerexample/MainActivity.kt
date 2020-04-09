package com.android.workmanagerexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.android.workmanagerexample.worker.NotificationWorker


class MainActivity : AppCompatActivity() {
    companion object{
        val MESSAGE_STATUS = "message_status"
    }
    lateinit var tvStatus: TextView
    lateinit var btnSend: Button


    @SuppressLint("IdleBatteryChargingConstraints")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvStatus = findViewById(R.id.tvStatus);
        btnSend = findViewById(R.id.btnSend);

        val mWorkManager = WorkManager.getInstance()


        val mRequest =
            OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                .build()
        btnSend.setOnClickListener {
            mWorkManager.enqueue(mRequest)
        }

        mWorkManager.getWorkInfoByIdLiveData(mRequest.id).observe(this, Observer {workInfo ->

            if(workInfo!=null){
                val state=workInfo.state
                tvStatus.append(state.toString() + "\n")

            }

        })
    }
}
