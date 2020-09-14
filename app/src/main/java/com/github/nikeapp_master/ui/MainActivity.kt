package com.github.nikeapp_master.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.nikeapp_master.R
import com.github.nikeapp_master.model.Items
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_view.*


class MainActivity : AppCompatActivity(), Communicator {

    private lateinit var myViewModel: PostListViewModel
    private lateinit var myViewModelProvider: PostViewModel
    private var playSound = PlaySound()
    private var activate = false
    private var errorSnackBar: Snackbar? = null
    private val errorClickListener = View.OnClickListener { getQuery("wat", true) }

    private var queryFragment = QueryFragment()
    private var searchFragment = SearchFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentView, queryFragment)
            .commit()
        supportFragmentManager.beginTransaction().replace(R.id.searchFragment, searchFragment)
            .commit()
        searchFragment.search = "wat"
        getQuery("wat", true)

    }

    override fun showFragment() {
        activate = true
        boxSort.visibility = View.VISIBLE
        fragmentView.visibility = View.VISIBLE
        searchImage.visibility = View.GONE
        sound_Icon.visibility = View.VISIBLE

    }

    override fun getQuery(query: String, sort: Boolean) {
        myViewModel = ViewModelProvider(this).get(PostListViewModel::class.java)
        myViewModel.getQuery(query)
        val preloadingObserver = Observer<Boolean> {
            if (it) {
                progressSort.visibility = View.VISIBLE
                toggleButton.visibility = View.GONE

            } else {
                progressSort.visibility = View.GONE
                toggleButton.visibility = View.VISIBLE
            }
        }
        val offLineObserver = Observer<Boolean> {
            if (it) {
                showError()
                logoOffline.visibility = View.VISIBLE
                tv_search.visibility = View.GONE
                searchBtn.visibility = View.GONE

            } else {
                hideError()
                logoOffline.visibility = View.GONE
                tv_search.visibility = View.VISIBLE
                searchBtn.visibility = View.VISIBLE
            }
        }
        val noSavedDataObserver = Observer<Boolean> {
            if (it) {
                offlineImg.visibility = View.VISIBLE
                toggleButton.visibility = View.GONE
            } else {
                offlineImg.visibility = View.GONE
            }
        }

        val queryObserver = Observer<Items> { items ->
            queryFragment.loadQuery(items, sort)
            searchFragment.playinMusic(items, activate)
            SaveSounds(items)
            sound_Icon.setOnClickListener {
                playSound.items = items
                playSound.playSound()
            }

        }
        val saveJsonDataObserver = Observer<String> {
            saveJson(it)
        }
        val errorObservable = Observer<Boolean> {
            if (!it) {
                errorFlagBox.visibility = View.VISIBLE
                sound_Error.visibility = View.VISIBLE
                progressSort.visibility = View.GONE
                toggleButton.visibility = View.GONE
                ladyError.visibility = View.VISIBLE
                fragmentViewBox.visibility = View.GONE
                sound_Icon.visibility = View.GONE
                sound_Error.visibility = View.VISIBLE
            } else {
                sound_Error.visibility = View.GONE
                fragmentViewBox.visibility = View.VISIBLE
                ladyError.visibility = View.GONE
                errorFlagBox.visibility = View.GONE
                sound_Error.visibility = View.GONE
            }

        }
        myViewModel.getPreLoadingLiveData().observe(this, preloadingObserver)
        myViewModel.getOffLineLiveData().observe(this, offLineObserver)
        myViewModel.getQueryLiveData().observe(this, queryObserver)
        myViewModel.getSaveJsonLiveData().observe(this, saveJsonDataObserver)
        myViewModel.getNoSavedMutableLiveData().observe(this, noSavedDataObserver)
        myViewModel.getErrorLiveData().observe(this, errorObservable)

    }

    private fun saveJson(data: String) {
        myViewModelProvider = ViewModelProvider(this).get(PostViewModel::class.java)
        myViewModelProvider.saveJson(this, data)
    }

    override fun playSound(url: String) {
        soundPlayer.setVideoPath(url)
        soundPlayer.start()
    }

    private fun showError() {
        errorSnackBar = Snackbar.make(root_layout, "Connection Error", Snackbar.LENGTH_INDEFINITE)
        errorSnackBar?.setAction("Retry", errorClickListener)
        errorSnackBar?.show()
    }

    private fun hideError() {
        errorSnackBar?.dismiss()
    }

    override fun playError() {
        val path = "android.resource://" + packageName + "/" + R.raw.errorsound
        soundPlayer.setVideoPath(path)
        soundPlayer.start()
    }

}