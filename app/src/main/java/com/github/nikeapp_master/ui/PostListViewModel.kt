package com.github.nikeapp_master.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.nikeapp_master.model.Items
import com.github.nikeapp_master.model.SaveJson
import com.github.nikeapp_master.network.Api
import com.github.nikeapp_master.viewmodel.MyViewModel
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class PostListViewModel() : MyViewModel() {
    @Inject
    lateinit var api: Api
    val TAG: String = "TAG"
    var queryLiveData = MutableLiveData<Items>()
    var preLoading = MutableLiveData<Boolean>()


    fun getQuery(query: String) {
        api.getRecipes(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Items> {
                override fun onSubscribe(d: Disposable?) {
                    preLoading.value = true
                }

                override fun onNext(t: Items?) {
                    queryLiveData.value = t
                    var json = GsonBuilder().setPrettyPrinting().create().toJson(t)
                    Log.d(TAG, "loadItems: $json")
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ${e?.message.toString()}")
                }

                override fun onComplete() {
                    preLoading.value = false
                }
            })
    }

    fun getQueryLiveData(): LiveData<Items>{
        return queryLiveData
    }
    fun getPreLoadingLiveData(): LiveData<Boolean>{
        return preLoading
    }


}