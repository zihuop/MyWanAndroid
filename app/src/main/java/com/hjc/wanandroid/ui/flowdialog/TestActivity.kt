package com.hjc.wanandroid.ui.flowdialog

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.hjc.wanandroid.R
import com.hjc.wanandroid.ui.flowdialog.JobTaskManager.jobMap
import com.hjc.wanandroid.ui.flowdialog.SingleJob
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class TestActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    var curLevel = 1
    val stateFlow = MutableStateFlow(curLevel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        findViewById<Button>(R.id.button).setOnClickListener {
            Toast.makeText(this, "12121", Toast.LENGTH_SHORT).show()
            MainScope().launch {
                JobTaskManager.apply {
                    stateFlow.collect {
                        flow {
                            emit(jobMap[it])
                        }.collect {
                            doJob(this@TestActivity, it!!)
                        }
                    }
                }
            }
        }
    }

    fun doJob(context: Context, job: SingleJob) {
        if (job.handle()) {
            job.launch(context) {
                curLevel++
                if (curLevel <= jobMap.size)
                    stateFlow.value = curLevel
            }
        } else {
            curLevel++
            if (curLevel <= jobMap.size)
                stateFlow.value = curLevel
        }
    }
}