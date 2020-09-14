package com.github.nikeapp_master.ui

import android.annotation.SuppressLint
import android.util.Log
import com.github.nikeapp_master.model.Items
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class SaveSounds(itemsList: Items) {
    private var folder = "wat"
    private var urlSong = ""
    private var listSongs: List<Any> = emptyList()

    init {
        saveSounds(itemsList)
        Log.d("TAG", "saved created: ")
    }

    @SuppressLint("SdCardPath")
    fun saveSounds(items: Items) {

        val lista = items.list
        var numberSound = 0
        for (shadowed in lista) {
            //Log.d(TAG, "saveSounds: ${items.sound_urls.size}")
            numberSound = shadowed.sound_urls.size
            listSongs = shadowed.sound_urls
            urlSong = shadowed.sound_urls.toString()
            folder = shadowed.word
        }
        val createFolder = File("/data/data/com.github.nikeapp_master/files/$folder")
        createFolder.mkdir()
        for (i in 0 until numberSound) {
            //Log.d(TAG, "saveSounds: ${listSongs[i]}")
            val thread = Thread {
                try {
                    URL(listSongs[i].toString()).openStream()
                        .use { input ->
                            FileOutputStream(File("/data/data/com.github.nikeapp_master/files/$folder/audio$i.wav")).use { output ->
                                input.copyTo(output)
                            }
                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            thread.start()

        }
    }
}
