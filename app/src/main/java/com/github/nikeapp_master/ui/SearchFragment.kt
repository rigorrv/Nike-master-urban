package com.github.nikeapp_master.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nikeapp_master.R
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.search_view.*
import kotlinx.android.synthetic.main.search_view.view.*
import kotlinx.android.synthetic.main.search_view.view.searchBtn

class SearchFragment : Fragment() {

    var sort: Boolean = true
    lateinit var rootView: View
    lateinit var communicator: Communicator
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        communicator = activity as Communicator
        rootView = inflater.inflate(R.layout.search_view, container, false)
        rootView.searchBtn.setOnClickListener {
            communicator.getQuery(tv_search.text.toString(), true)
        }
        rootView.toggleButton.setOnClickListener {
            sort = !sort
            if (tv_search.text.isEmpty())
                communicator.getQuery("wat", sort)
            else
                communicator.getQuery(tv_search.text.toString(), sort)

        }
        return rootView
    }
}