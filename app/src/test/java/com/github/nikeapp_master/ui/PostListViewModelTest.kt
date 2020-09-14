package com.github.nikeapp_master.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.github.nikeapp_master.di.DaggerTestAppComponent
import com.github.nikeapp_master.injection.NetworkModule
import com.github.nikeapp_master.model.ItemTest
import com.github.nikeapp_master.model.Items
import com.github.nikeapp_master.network.Api
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import javax.inject.Inject


@Suppress("DEPRECATION")
class PostListViewModelTest {

    @Inject
    lateinit var appAPIs: Api

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var viewModel: PostListViewModel
    private val itemsObservers: Observer<Items> = mock()
    private val stringObserver: Observer<String> = mock()
    private val preloadingObserver: Observer<Boolean> = mock()
    private val offLineLiveData: Observer<Boolean> = mock()
    private val noSavedFile: Observer<Boolean> = mock()
    private val itemTest: Observer<ItemTest> = mock()
    private val errorLiveData: Observer<Boolean> = mock()

    @Before
    fun before() {
        viewModel = PostListViewModel()
        viewModel.queryLiveData.observeForever(itemsObservers)
        viewModel.saveJsonLiveData.observeForever(stringObserver)
        viewModel.preLoading.observeForever(preloadingObserver)
        viewModel.offLineLiveData.observeForever(offLineLiveData)
        viewModel.noSavedFile.observeForever(noSavedFile)
        viewModel.itemLiveData.observeForever(itemTest)
        viewModel.errorLiveData.observeForever(errorLiveData)

    }

    @Before
    fun setUp() {
        val injector = DaggerTestAppComponent
            .builder()
            .networkModule(NetworkModule)
            .build()
        injector.into(this)
    }



    @Test
    fun fetchUser_ShouldReturnUser() {
        appAPIs = mock()
        val captor = appAPIs.getRecipesTesting("")
        captor.run {
            verify(appAPIs).getRecipesTesting("")
            assertEquals(appAPIs, appAPIs)
        }
    }


    @Test
    fun setQueryLiveData() {
        val expectedUser = Items(listOf())
        viewModel.setQueryLiveData(Items(listOf()))

        val captor = ArgumentCaptor.forClass(Items::class.java)
        captor.run {
            verify(itemsObservers, times(1)).onChanged(capture())
            assertEquals(expectedUser, value)
        }
    }

    @Test
    fun setSaveJsonLiveData() {
        val expectedUser = ""
        viewModel.setSaveJsonLiveData(expectedUser)
        val captor = String()
        captor.run {
            verify(stringObserver, times(1)).onChanged("")
            assertEquals(expectedUser, "")
        }
    }

    @Test
    fun setPreLoadingLiveData() {
        val expectedUser = true
        viewModel.setPreLoadingLiveData(expectedUser)
        val captor = Boolean
        captor.run {
            verify(preloadingObserver, times(1)).onChanged(true)
            assertEquals(expectedUser, true)
        }
    }

    @Test
    fun setOffLineLiveData() {
        val expectedUser = true
        viewModel.setOffLineLiveData(expectedUser)
        val captor = Boolean
        captor.run {
            verify(offLineLiveData, times(1)).onChanged(true)
            assertEquals(expectedUser, true)
        }
    }

    @Test
    fun setNoSavedMutableLiveData() {
        val expectedUser = true
        viewModel.setNoSavedMutableLiveData(expectedUser)
        val captor = Boolean
        captor.run {
            verify(noSavedFile, times(1)).onChanged(true)
            assertEquals(expectedUser, true)
        }
    }

    @Test
    fun setErrorMutableLiveData() {
        val expectedUser = true
        viewModel.setErrorLiveData(expectedUser)
        val captor = Boolean
        captor.run {
            verify(errorLiveData, times(1)).onChanged(true)
            assertEquals(expectedUser, true)
        }
    }
}