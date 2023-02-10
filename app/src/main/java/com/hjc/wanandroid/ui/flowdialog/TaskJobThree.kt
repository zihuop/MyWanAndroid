package com.hjc.wanandroid.ui.flowdialog

import android.app.AlertDialog
import android.content.Context

class TaskJobThree : SingleJob {
    override fun handle(): Boolean {
        println("start handle job three")
        return true
    }
    override fun launch(context: Context, callback: () -> Unit) {
        println("start launch job one")
        AlertDialog.Builder(context).setMessage("这是第三个弹框")
            .setPositiveButton("ok") {x,y->
                callback()
            }.show()
    }
}