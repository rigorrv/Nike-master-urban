package com.github.nikeapp_master.injection

import androidx.recyclerview.widget.RecyclerView
import com.github.nikeapp_master.network.Api
import com.github.nikeapp_master.ui.DefinitionAdapter
import dagger.Module
import dagger.Provides
import dagger.Reusable
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@Suppress("unused")
object NetworkModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun providesApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }



    @Provides
    @Reusable
    @JvmStatic
    internal fun providesRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://mashape-community-urban-dictionary.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun recyclerView(recyclerView: RecyclerView) : DefinitionAdapter {
        return recyclerView(recyclerView)
    }


}