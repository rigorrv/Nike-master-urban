package com.github.nikeapp_master.network

import com.github.nikeapp_master.model.Items
import com.github.nikeapp_master.model.ItemsPojo
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {
    @GET("define")

    @Headers("x-rapidapi-host: mashape-community-urban-dictionary.p.rapidapi.com", "x-rapidapi-key: 62e2eab0d7mshe0129a42c2d722ap119deejsn4d4b8a17a024")
    fun getRecipes(@Query("term") query: String): Observable<Items>

    @Headers("x-rapidapi-host: mashape-community-urban-dictionary.p.rapidapi.com", "x-rapidapi-key: 62e2eab0d7mshe0129a42c2d722ap119deejsn4d4b8a17a024")
    fun getApi(@Query("term") query: String): Observable<ItemsPojo>
}

