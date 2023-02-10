package com.hjc.wanandroid.ui.flowdialog

import android.content.Context

interface SingleJob {
    fun handle(): Boolean
    fun launch(context: Context, callback: () -> Unit)
}