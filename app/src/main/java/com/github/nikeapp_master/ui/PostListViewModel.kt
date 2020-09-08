package com.github.nikeapp_master.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.nikeapp_master.model.Items
import com.github.nikeapp_master.model.SaveJson
import com.github.nikeapp_master.network.Api
import com.github.nikeapp_master.viewmodel.MyViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import javax.inject.Inject
import kotlin.jvm.Throws

class PostListViewModel() : MyViewModel() {
    @Inject
    lateinit var api: Api
    val TAG: String = "TAG"
    var queryLiveData = MutableLiveData<Items>()
    var preLoading = MutableLiveData<Boolean>()
    var saveJsonLiveData = MutableLiveData<String>()
    var offLineLiveData = MutableLiveData<Boolean>()
    var noSavedFile = MutableLiveData<Boolean>()

    @Throws(InterruptedException::class, IOException::class)
    fun isConnected(): Boolean {
        val commadn = "ping -c 1 urbandictionary.com"
        return Runtime.getRuntime().exec(commadn).waitFor() == 0
    }

    fun getQuery(query: String) {
        if (isConnected()) {
            api.getRecipes(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Items> {
                    override fun onSubscribe(d: Disposable?) {
                        preLoading.value = true
                    }

                    override fun onNext(t: Items?) {
                        queryLiveData.value = t
                        saveJsonLiveData.value =
                            GsonBuilder().setPrettyPrinting().create().toJson(t)
                    }

                    override fun onError(e: Throwable?) {
                        Log.d(TAG, "onError: ${e?.message.toString()}")
                    }

                    override fun onComplete() {
                        preLoading.value = false
                        offLineLiveData.value = false
                        noSavedFile.value = false
                    }
                })
        } else {
            loadLocal()
        }
    }


    private fun loadLocal() {
        offLineLiveData.value = true
        noSavedFile.value = false
        val gson = Gson()
        val fileName = "/data/data/com.github.nikeapp_master/files/codebeautify.json"
        var file = File(fileName)
        var fileExists = file.exists()
        if (fileExists) {
            try {
                val br = BufferedReader(
                    FileReader("/data/data/com.github.nikeapp_master/files/codebeautify.json")
                )
                val obj: Items = gson.fromJson(br, Items::class.java)
                queryLiveData.value = obj
                noSavedFile.value = false
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            Log.d(TAG, "loadLocal: $fileName doesn't exist")
            noSavedFile.value = true

        }


    }


    fun getQueryLiveData(): LiveData<Items> {
        return queryLiveData
    }

    fun getPreLoadingLiveData(): LiveData<Boolean> {
        return preLoading
    }

    fun getSaveJsonLiveData(): LiveData<String> {
        return saveJsonLiveData
    }

    fun getOffLineLiveData(): LiveData<Boolean> {
        return offLineLiveData
    }

    fun getNoSavedMutableLiveData () : LiveData<Boolean>{
        return noSavedFile
    }

}