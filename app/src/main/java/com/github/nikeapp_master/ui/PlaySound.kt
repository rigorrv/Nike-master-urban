package com.github.nikeapp_master.ui

import android.annotation.SuppressLint
import com.github.nikeapp_master.model.Items
import java.io.File

class PlaySound {
    lateinit var items : Items
    private var track = 0
    private var sound = ""
    private var urlSound =""
    private var folder = "wat"

    @SuppressLint("SdCardPath")
    fun playSound() {
        val createFolder = File("/data/data/com.github.nikeapp_master/files/$folder")
        createFolder.mkdir()
        for (name in items.list){
            folder = name.word
        }

        val folderFiles = File("/data/data/com.github.nikeapp_master/files/$folder/")
        val fileNames = folderFiles.list()

        for (i in fileNames!!.indices) {
            if (track < fileNames.size) {
                /* /nothing */
            }else{
                track = 0
            }
        }
        urlSound = "/data/data/com.github.nikeapp_master/files/$folder/audio$track.wav"
        sound = urlSound
        communicator.playSound(urlSound)

        track++

    }
}