package com.github.nikeapp_master.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.github.nikeapp_master.R
import com.github.nikeapp_master.model.Items
import kotlinx.android.synthetic.main.search_view.*
import kotlinx.android.synthetic.main.search_view.view.*


class SearchFragment : Fragment() {

    private var sort: Boolean = true
    var search = ""
    private lateinit var rootView: View
    private lateinit var communicator: Communicator
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        communicator = activity as Communicator
        rootView = inflater.inflate(R.layout.search_view, container, false)
        rootView.searchBtn.setOnClickListener {
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
            sort = !sort
            search = tv_search.text.toString()
            communicator.getQuery(tv_search.text.toString(), sort)
            tv_search.setText("")
            communicator.showFragment()
        }

        rootView.tv_search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sort = !sort
                communicator.getQuery(tv_search.text.toString(), sort)
                communicator.showFragment()
                imm.hideSoftInputFromWindow(view!!.windowToken, 0)
                search = tv_search.text.toString()
                tv_search.setText("")
                true
            } else false
        }

        rootView.sound_Error.setOnClickListener {
            communicator.playError()
        }


        rootView.toggleButton.setOnClickListener {
            sort = !sort
            communicator.getQuery(search, sort)
        }
        return rootView
    }

    fun playinMusic(items: Items, activate: Boolean) {
        for (i: Items.Info in items.list) {
            if (activate) {
                if (i.sound_urls.isEmpty()) {
                    rootView.sound_Icon.visibility = View.GONE
                } else {
                    rootView.sound_Icon.visibility = View.VISIBLE
                }
            }
        }
    }
}