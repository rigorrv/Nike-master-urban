package com.github.nikeapp_master.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nikeapp_master.R
import com.github.nikeapp_master.model.Items
import kotlinx.android.synthetic.main.query_fragment.*


lateinit var communicator: Communicator

class QueryFragment : Fragment() {

    private val queryAdapter = DefinitionAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        communicator = activity as Communicator
        return inflater.inflate(R.layout.query_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queryRecyclerView.adapter = queryAdapter
    }

    fun loadQuery(queryList: Items, sortList: Boolean) {
        if (sortList)
            queryAdapter.setQueries(queryList.list.sortedBy { it.thumbs_up })
         else
            queryAdapter.setQueries(queryList.list.sortedByDescending { it.thumbs_up })
    }

}
