package com.github.nikeapp_master.injection

import com.github.nikeapp_master.ui.DefinitionAdapter
import com.github.nikeapp_master.ui.PostListViewModel
import com.github.nikeapp_master.ui.PostViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun injection(postListViewModel: PostListViewModel)
    fun injection(postViewModel: PostViewModel)
    fun injection(queryFragment: DefinitionAdapter)


    @Component.Builder
    interface Builder{
        fun build() : ViewModelInjector
        fun networkModule(networkModule : NetworkModule):Builder
    }


}