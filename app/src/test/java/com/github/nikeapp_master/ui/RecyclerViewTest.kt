package com.github.nikeapp_master.ui

import com.github.nikeapp_master.model.Items
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import javax.inject.Inject


class RecyclerViewTest {

    @Inject
    lateinit var postDefinitionAdapter : DefinitionAdapter
    private val moreThumbsUP = "More Thumbs Up"
    private val moreThumbsDown = "More Thumbs Down"
    private val adapter = DefinitionAdapter()

    private val mockList: List<Items.Info> =
        mutableListOf(
            Items.Info("", "", 0, "", "", "", mutableListOf(), 10, 5, moreThumbsDown, ""),
            Items.Info("", "", 0, "", "", "", mutableListOf(), 5, 10, moreThumbsUP, "")
        )

    @Test
    fun itemCount() {
        adapter.queryList = mockList
        assertThat(adapter.itemCount.toString(),true)
    }

}

