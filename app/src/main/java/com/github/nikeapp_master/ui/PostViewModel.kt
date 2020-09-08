package com.github.nikeapp_master.ui

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.nikeapp_master.injection.DaggerViewModelInjector
import com.github.nikeapp_master.injection.NetworkModule
import com.github.nikeapp_master.injection.ViewModelInjector
import com.github.nikeapp_master.viewmodel.MyViewModel
import java.io.IOException
import java.io.OutputStreamWriter

class PostViewModel : MyViewModel() {

    fun saveJson( myActivity: AppCompatActivity,  myData: String) {
        try {
            val outputStreamWriter = OutputStreamWriter(
                myActivity.openFileOutput(
                    "codebeautify.json", Context.MODE_PRIVATE
                )
            )
            outputStreamWriter.write(myData)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: $e")
        }
    }


}