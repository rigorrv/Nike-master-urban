package com.github.nikeapp_master.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.nikeapp_master.model.ItemTest
import com.github.nikeapp_master.model.Items
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

class PostListViewModel : MyViewModel() {

    @Inject
    lateinit var api: Api
    var queryLiveData = MutableLiveData<Items>()
    var saveJsonLiveData = MutableLiveData<String>()
    var preLoading = MutableLiveData<Boolean>()
    var offLineLiveData = MutableLiveData<Boolean>()
    var noSavedFile = MutableLiveData<Boolean>()
    var errorLiveData = MutableLiveData<Boolean>()
    //for test
    var itemLiveData = MutableLiveData<ItemTest>()


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
                        setPreLoadingLiveData(true)
                    }

                    override fun onNext(t: Items?) {
                        setErrorLiveData(true)
                        setQueryLiveData(t!!)
                        setSaveJsonLiveData(GsonBuilder().setPrettyPrinting().create().toJson(t))
                    }

                    override fun onError(e: Throwable?) {
                        setErrorLiveData(false)
                    }

                    override fun onComplete() {
                        setPreLoadingLiveData(false)
                        setOffLineLiveData(false)
                        setNoSavedMutableLiveData(false)

                    }
                })
        } else {
            loadLocal()
        }
    }

    @SuppressLint("SdCardPath")
    private fun loadLocal() {


        setOffLineLiveData(true)
        setNoSavedMutableLiveData(false)

        val gson = Gson()
        val fileName = "/data/data/com.github.nikeapp_master/files/codebeautify.json"
        val file = File(fileName)
        val fileExists = file.exists()
        if (fileExists) {
            try {
                val br = BufferedReader(
                    FileReader("/data/data/com.github.nikeapp_master/files/codebeautify.json")
                )
                val obj: Items = gson.fromJson(br, Items::class.java)
                setQueryLiveData(obj)
                setNoSavedMutableLiveData(false)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            setNoSavedMutableLiveData(true)
        }
    }

    fun setQueryLiveData(items: Items) {
        queryLiveData.value = items
    }

    fun setSaveJsonLiveData(items: String) {
        saveJsonLiveData.value = items
    }

    fun setPreLoadingLiveData(value: Boolean) {
        preLoading.value = value
    }

    fun setOffLineLiveData(value: Boolean) {
        offLineLiveData.value = value
    }

    fun setNoSavedMutableLiveData(data: Boolean) {
        noSavedFile.value = data
    }
    fun setErrorLiveData(error: Boolean) {
        errorLiveData.value = error
    }

    fun getQueryLiveData(): LiveData<Items> {
        return queryLiveData
    }

    fun getSaveJsonLiveData(): LiveData<String> {
        return saveJsonLiveData
    }

    fun getPreLoadingLiveData(): LiveData<Boolean> {
        return preLoading
    }

    fun getOffLineLiveData(): LiveData<Boolean> {
        return offLineLiveData
    }

    fun getNoSavedMutableLiveData(): LiveData<Boolean> {
        return noSavedFile
    }

    fun getErrorLiveData(): LiveData<Boolean> {
        return errorLiveData
    }


}