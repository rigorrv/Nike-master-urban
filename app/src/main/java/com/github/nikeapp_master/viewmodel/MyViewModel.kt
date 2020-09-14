package com.github.nikeapp_master.viewmodel

import androidx.lifecycle.ViewModel
import com.github.nikeapp_master.injection.DaggerViewModelInjector
import com.github.nikeapp_master.injection.NetworkModule
import com.github.nikeapp_master.injection.ViewModelInjector
import com.github.nikeapp_master.ui.PostListViewModel
import com.github.nikeapp_master.ui.PostViewModel

abstract class MyViewModel : ViewModel(){

    private val injector : ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when(this){
            is PostListViewModel -> injector.injection(this)
            is PostViewModel -> injector.injection(this)
        }
    }
}