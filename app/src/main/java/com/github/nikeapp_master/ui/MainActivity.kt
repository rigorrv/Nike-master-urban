package com.github.nikeapp_master.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.nikeapp_master.R
import com.github.nikeapp_master.injection.ViewModelFactory
import com.github.nikeapp_master.model.Items
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_view.*


class MainActivity : AppCompatActivity(), Communicator {
    val TAG: String = "TAG"
    lateinit var myViewModel: PostListViewModel
    lateinit var myViewModelProvider: PostViewModel
    private var errorSnackBar: Snackbar? = null
    val errorClickListener = View.OnClickListener { getQuery("wat", true) }


    var queryFragment = QueryFragment()
    var searchFragment = SearchFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentView, queryFragment)
            .commit()
        supportFragmentManager.beginTransaction().replace(R.id.searchFragment, searchFragment)
            .commit()
        getQuery("wat", true)

    }

    override fun getQuery(query: String, sort: Boolean) {
        myViewModel = ViewModelProvider(this).get(PostListViewModel::class.java)
        myViewModel.getQuery(query)
        var preloadingObserver = Observer<Boolean> {
            if (it) {
                progressSort.visibility = View.VISIBLE
            } else {
                progressSort.visibility = View.GONE
                toggleButton.visibility = View.VISIBLE
            }
        }
        var offLineObserver = Observer<Boolean> {
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
        var noSavedDataObserver = Observer<Boolean> {
            if (it) {
                offlineImg.visibility = View.VISIBLE
                toggleButton.visibility = View.GONE
            } else {
                offlineImg.visibility = View.GONE
            }
        }

        var queryObserver = Observer<Items> {
            queryFragment.loadQuery(it, sort)
        }
        var saveJsonDataObserver = Observer<String> {
            saveJson(it)
        }
        myViewModel.getPreLoadingLiveData().observe(this, preloadingObserver)
        myViewModel.getOffLineLiveData().observe(this, offLineObserver)
        myViewModel.getQueryLiveData().observe(this, queryObserver)
        myViewModel.getSaveJsonLiveData().observe(this, saveJsonDataObserver)
        myViewModel.getNoSavedMutableLiveData().observe(this, noSavedDataObserver)

    }

    fun saveJson(data: String) {
        myViewModelProvider =
            ViewModelProvider(this, ViewModelFactory(this, data)).get(PostViewModel::class.java)
        myViewModelProvider.saveJson(this, data)
    }

    fun showError() {
        errorSnackBar = Snackbar.make(root_layout, "Connection Error", Snackbar.LENGTH_INDEFINITE)
        errorSnackBar?.setAction("Retry", errorClickListener)
        errorSnackBar?.show()
    }

    fun hideError() {
        errorSnackBar?.dismiss()
    }


}