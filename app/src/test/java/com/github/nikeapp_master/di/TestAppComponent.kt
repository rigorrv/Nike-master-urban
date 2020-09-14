package com.github.nikeapp_master.di

import com.github.nikeapp_master.injection.NetworkModule
import com.github.nikeapp_master.injection.ViewModelInjector
import com.github.nikeapp_master.ui.DefinitionAdapter
import com.github.nikeapp_master.ui.PostListViewModelTest
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(NetworkModule::class)])
interface TestAppComponent : ViewModelInjector {
    fun into(appRepositoryTest: PostListViewModelTest)
    fun into(queryFragment: DefinitionAdapter)


}