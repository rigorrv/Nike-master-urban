package com.github.nikeapp_master.ui

import android.R.attr.password
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import com.github.nikeapp_master.R
import kotlinx.android.synthetic.main.search_view.*
import kotlinx.android.synthetic.main.search_view.view.*


class SearchFragment : Fragment() {

    var sort: Boolean = true
    lateinit var rootView: View
    lateinit var communicator: Communicator
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        communicator = activity as Communicator
        rootView = inflater.inflate(R.layout.search_view, container, false)
        rootView.searchBtn.setOnClickListener {
            imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
            communicator.getQuery(tv_search.text.toString(), true)
        }

        rootView.tv_search.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                communicator.getQuery(tv_search.text.toString(), true)
                imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
                true
            } else false
        })

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