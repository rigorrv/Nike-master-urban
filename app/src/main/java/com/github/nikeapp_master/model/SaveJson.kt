package com.github.nikeapp_master.model

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.io.OutputStreamWriter


interface SaveJson{
    fun saveJson(data : String)

}