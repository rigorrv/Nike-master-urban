package com.github.nikeapp_master.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.nikeapp_master.R
import com.github.nikeapp_master.injection.ViewModelFactory
import com.github.nikeapp_master.model.Items
import kotlinx.android.synthetic.main.search_view.*
import java.io.IOException
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity(), Communicator {
    var data : String =""
    lateinit var myViewModel: PostListViewModel
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
        var preloadingObserver = Observer<Boolean> {
            if (it) {
                progressSort.visibility = View.VISIBLE
            } else {
                progressSort.visibility = View.GONE
                toggleButton.visibility = View.VISIBLE
            }
        }
        myViewModel = ViewModelProvider(this).get(PostListViewModel::class.java)
        myViewModel.getPreLoadingLiveData().observe(this, preloadingObserver)
        myViewModel.getQuery(query)
        var queryObserver = Observer<Items> {
            queryFragment.loadQuery(it, sort)
            data = it.toString()
        }
        myViewModel.getQueryLiveData().observe(this, queryObserver)
        myViewModel = ViewModelProvider(this, ViewModelFactory(this, data)).get(PostListViewModel::class.java)
    }



    override fun saveJson(data: String) {

    }
}